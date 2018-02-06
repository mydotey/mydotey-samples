package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.ObjectPoolEntry;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPoolEntry.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoScaleObjectPool extends ObjectPool implements Closeable {

    private static Logger _logger = LoggerFactory.getLogger(AutoScaleObjectPool.class);

    protected ScheduledExecutorService _scheduledExecutorService;

    public AutoScaleObjectPool(AutoScaleObjectPoolConfig config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

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
            if (entry.getStatus() == Status.PENDING_CLOSE) {
                if (!tryRefresh(entry))
                    scaleIn(entry);
            } else
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

            scaleIn(entry);
            return true;
        }
    }

    protected void scaleIn(AutoScaleObjectPoolEntry entry) {
        _entries.remove(entry.getNumber());
        _numberPool.add(entry.getNumber());
        close(entry);
    }

    protected boolean tryRefresh(Integer number) {
        AutoScaleObjectPoolEntry entry = getEntry(number);
        boolean needRefresh = isExpired(entry) || isStale(entry);
        if (!needRefresh)
            return false;

        synchronized (number) {
            entry = getEntry(number);
            needRefresh = isExpired(entry) || isStale(entry);
            if (!needRefresh)
                return false;

            if (entry.getStatus() == Status.AVAILABLE)
                return tryRefresh(entry);

            entry.setStatus(Status.PENDING_CLOSE);
            return false;
        }
    }

    protected boolean tryRefresh(AutoScaleObjectPoolEntry entry) {
        AutoScaleObjectPoolEntry newEntry = null;
        try {
            newEntry = newPoolEntry(entry.getNumber());
        } catch (Exception e) {
            _logger.error("failed to get object from object factory", e);
            return false;
        }

        close(entry);
        _entries.put(entry.getNumber(), newEntry);

        return true;
    }

    protected void close(AutoScaleObjectPoolEntry entry) {
        if (entry.getObject() instanceof Closeable) {
            try {
                ((Closeable) entry.getObject()).close();
            } catch (Exception e) {
                _logger.warn("close object failed", e);
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
            _logger.warn("failed to invoke staleChecker, ignore the check", e);
            return false;
        }
    }

}
