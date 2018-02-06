package org.mydotey.samples.designpattern.objectpool.bak.autorefreshed;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mydotey.samples.designpattern.objectpool.bak.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.bak.ObjectPoolEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoRefreshedObjectPool extends ObjectPool implements Closeable {

    private static Logger _logger = LoggerFactory.getLogger(AutoRefreshedObjectPool.class);

    protected ScheduledExecutorService _scheduledExecutorService;

    public AutoRefreshedObjectPool(AutoRefreshedObjectPoolConfig config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

        _scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        _scheduledExecutorService.scheduleWithFixedDelay(() -> AutoRefreshedObjectPool.this.refresh(),
                getConfig().getStaleCheckInterval(), getConfig().getStaleCheckInterval(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        _scheduledExecutorService.shutdown();
    }

    @Override
    protected AutoRefreshedObjectPoolEntry newPoolEntry(Integer index) {
        return new AutoRefreshedObjectPoolEntry(index, newObject());
    }

    @Override
    public AutoRefreshedObjectPoolConfig getConfig() {
        return (AutoRefreshedObjectPoolConfig) super.getConfig();
    }

    @Override
    protected ObjectPoolEntry acquire(Integer index) {
        synchronized (index) {
            tryRefresh((AutoRefreshedObjectPoolEntry) _entries[index]);
            _freeIndexes.remove(index);
            _usedIndexes.add(index);
            return _entries[index];
        }
    }

    @Override
    protected void resetEntry(ObjectPoolEntry entry) {
        AutoRefreshedObjectPoolEntry concreteEntry = (AutoRefreshedObjectPoolEntry) entry;
        synchronized (concreteEntry.getIndex()) {
            if (concreteEntry.isClosable()) {
                close(concreteEntry);
                return;
            }

            _entries[concreteEntry.getIndex()] = concreteEntry.clone();
            _usedIndexes.remove(concreteEntry.getIndex());
            _freeIndexes.add(concreteEntry.getIndex());
        }
    }

    protected void refresh() {
        for (int i = 0; i < _size; i++) {
            tryRefresh((AutoRefreshedObjectPoolEntry) _entries[i]);
        }
    }

    protected boolean tryRefresh(AutoRefreshedObjectPoolEntry entry) {
        boolean needRefresh = isExpired(entry) || isStale(entry);
        if (!needRefresh)
            return false;

        synchronized (entry.getIndex()) {
            _freeIndexes.remove(entry.getIndex());
            boolean closable = !_usedIndexes.remove(entry.getIndex());
            if (closable) {
                close(entry);
            } else {
                entry.setClosable();
            }

            _entries[entry.getIndex()] = newPoolEntry(entry.getIndex());
            _freeIndexes.add(entry.getIndex());
        }

        return true;
    }

    protected void close(ObjectPoolEntry entry) {
        if (entry.getObject() instanceof Closeable) {
            try {
                ((Closeable) entry.getObject()).close();
            } catch (Exception e) {
                _logger.warn("close object failed.", e);
            }
        }
    }

    protected boolean isExpired(AutoRefreshedObjectPoolEntry entry) {
        return entry.getCreationTime() + getConfig().getObjectTtl() <= System.currentTimeMillis();
    }

    protected boolean isStale(AutoRefreshedObjectPoolEntry entry) {
        try {
            return getConfig().getStaleChecker().isStale(entry.getObject());
        } catch (Exception e) {
            _logger.warn("staleChecker thrown exception, ignore the check.", e);
            return false;
        }
    }

}
