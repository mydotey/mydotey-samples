package org.mydotey.samples.designpattern.objectpool.autoscale.autorefreshed;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mydotey.samples.designpattern.objectpool.autoscale.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.autoscale.ObjectPoolEntry;
import org.mydotey.samples.designpattern.objectpool.autoscale.autorefreshed.AutoScaleObjectPoolEntry.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoScaleObjectPool extends ObjectPool implements Closeable {

    private static Logger _logger = LoggerFactory.getLogger(AutoScaleObjectPool.class);

    protected Object _scaleLock;
    protected ScheduledExecutorService _scheduledExecutorService;

    public AutoScaleObjectPool(AutoScaleObjectPoolConfig config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

        _scaleLock = new Object();
        _scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        _scheduledExecutorService.scheduleWithFixedDelay(() -> AutoScaleObjectPool.this.autoCheck(),
                getConfig().getStaleCheckInterval(), getConfig().getStaleCheckInterval(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        _scheduledExecutorService.shutdown();
    }

    @Override
    protected ObjectPoolEntry tryAddNewEntryAndAcquireOne() {
        ObjectPoolEntry entry = tryCreateNewEntry();
        if (entry != null)
            tryAddNewEntry(getConfig().getScaleFactor() - 1);

        return entry;
    }

    @Override
    protected AutoScaleObjectPoolEntry newPoolEntry(Integer number) {
        return new AutoScaleObjectPoolEntry(number, newObject());
    }

    @Override
    protected AutoScaleObjectPoolEntry getEntry(Integer number) {
        return (AutoScaleObjectPoolEntry) super.getEntry(number);
    }

    @Override
    public AutoScaleObjectPoolConfig getConfig() {
        return (AutoScaleObjectPoolConfig) super.getConfig();
    }

    @Override
    protected AutoScaleObjectPoolEntry acquire(Integer number) {
        synchronized (number) {
            boolean success = tryRefresh(number);
            AutoScaleObjectPoolEntry entry = (AutoScaleObjectPoolEntry) super.acquire(number);
            if (!success)
                entry.renew();

            return entry;
        }
    }

    @Override
    protected void releaseNumber(Integer number) {
        synchronized (number) {
            AutoScaleObjectPoolEntry entry = getEntry(number);
            if (entry.getStatus() == Status.PENDING_CLOSE)
                refresh(entry);
            else
                entry.renew();

            super.releaseNumber(number);
        }
    }

    protected void autoCheck() {
        for (Integer number : _entries.keySet()) {
            if (tryScaleIn(number))
                continue;

            tryRefresh(number);
        }
    }

    protected boolean tryScaleIn(Integer number) {
        AutoScaleObjectPoolEntry entry = getEntry(number);
        if (!needScaleIn(entry))
            return false;

        synchronized (number) {
            entry = getEntry(number);
            if (!needScaleIn(entry))
                return false;

            if (!_availableNumbers.remove(number))
                return false;

            _entries.remove(number);
            _numberPool.add(number);
            close(entry);

            return true;
        }
    }

    protected boolean tryRefresh(Integer number) {
        AutoScaleObjectPoolEntry entry = getEntry(number);
        boolean needRefresh = isExpired(entry) || isStale(entry);
        if (!needRefresh)
            return false;

        synchronized (number) {
            if (entry.getStatus() == Status.AVAILABLE) {
                refresh(entry);
                return true;
            }

            entry.setStatus(Status.PENDING_CLOSE);
            return false;
        }
    }

    protected void refresh(AutoScaleObjectPoolEntry entry) {
        close(entry);
        _entries.put(entry.getNumber(), newPoolEntry(entry.getNumber()));
    }

    protected void close(AutoScaleObjectPoolEntry entry) {
        if (entry.getObject() instanceof Closeable) {
            try {
                ((Closeable) entry.getObject()).close();
            } catch (Exception e) {
                _logger.warn("close object failed.", e);
            }
        }

        entry.setStatus(Status.CLOSED);
    }

    protected boolean isExpired(AutoScaleObjectPoolEntry entry) {
        return entry.getCreationTime() + getConfig().getObjectTtl() <= System.currentTimeMillis();
    }

    protected boolean needScaleIn(AutoScaleObjectPoolEntry entry) {
        return entry.getStatus() == Status.AVAILABLE
                && entry.getLastUsedTime() + getConfig().getMaxIdleTime() <= System.currentTimeMillis();
    }

    protected boolean isStale(AutoScaleObjectPoolEntry entry) {
        try {
            return getConfig().getStaleChecker().isStale(entry.getObject());
        } catch (Exception e) {
            _logger.warn("staleChecker thrown exception, ignore the check.", e);
            return false;
        }
    }

}
