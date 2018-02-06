package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mydotey.samples.designpattern.objectpool.DefaultObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class DefaultAutoScaleObjectPool extends DefaultObjectPool implements AutoScaleObjectPool {

    private static Logger _logger = LoggerFactory.getLogger(DefaultAutoScaleObjectPool.class);

    protected ScheduledExecutorService _scheduledExecutorService;

    public DefaultAutoScaleObjectPool(AutoScaleObjectPoolConfig config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

        _scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        _scheduledExecutorService.scheduleWithFixedDelay(() -> DefaultAutoScaleObjectPool.this.autoCheck(),
                getConfig().getStaleCheckInterval(), getConfig().getStaleCheckInterval(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() {
        _scheduledExecutorService.shutdown();
    }

    @Override
    protected DefaultEntry tryAddNewEntryAndAcquireOne() {
        DefaultEntry entry = tryCreateNewEntry();
        if (entry != null)
            tryAddNewEntry(getConfig().getScaleFactor() - 1);

        return entry;
    }

    @Override
    protected AutoScaleEntry newPoolEntry(Integer number) {
        return new AutoScaleEntry(number, newObject());
    }

    @Override
    protected AutoScaleEntry getEntry(Integer number) {
        return (AutoScaleEntry) super.getEntry(number);
    }

    @Override
    public AutoScaleObjectPoolConfig getConfig() {
        return (AutoScaleObjectPoolConfig) super.getConfig();
    }

    @Override
    protected AutoScaleEntry acquire(Integer number) {
        synchronized (number) {
            boolean success = tryRefresh(number);
            AutoScaleEntry entry = (AutoScaleEntry) super.acquire(number);
            if (!success)
                entry.renew();

            return entry;
        }
    }

    @Override
    protected void releaseNumber(Integer number) {
        synchronized (number) {
            AutoScaleEntry entry = getEntry(number);
            if (entry.getStatus() == AutoScaleEntry.Status.PENDING_CLOSE) {
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
        AutoScaleEntry entry = getEntry(number);
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

    protected void scaleIn(AutoScaleEntry entry) {
        _entries.remove(entry.getNumber());
        _numberPool.add(entry.getNumber());
        close(entry);
    }

    protected boolean tryRefresh(Integer number) {
        AutoScaleEntry entry = getEntry(number);
        boolean needRefresh = isExpired(entry) || isStale(entry);
        if (!needRefresh)
            return false;

        synchronized (number) {
            entry = getEntry(number);
            needRefresh = isExpired(entry) || isStale(entry);
            if (!needRefresh)
                return false;

            if (entry.getStatus() == AutoScaleEntry.Status.AVAILABLE)
                return tryRefresh(entry);

            entry.setStatus(AutoScaleEntry.Status.PENDING_CLOSE);
            return false;
        }
    }

    protected boolean tryRefresh(AutoScaleEntry entry) {
        AutoScaleEntry newEntry = null;
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

    protected void close(AutoScaleEntry entry) {
        if (entry.getObject() instanceof Closeable) {
            try {
                ((Closeable) entry.getObject()).close();
            } catch (Exception e) {
                _logger.warn("close object failed", e);
            }
        }

        entry.setStatus(AutoScaleEntry.Status.CLOSED);
    }

    protected boolean isExpired(AutoScaleEntry entry) {
        return entry.getCreationTime() + getConfig().getObjectTtl() <= System.currentTimeMillis();
    }

    protected boolean needScaleIn(AutoScaleEntry entry) {
        return entry.getStatus() == AutoScaleEntry.Status.AVAILABLE
                && entry.getLastUsedTime() + getConfig().getMaxIdleTime() <= System.currentTimeMillis();
    }

    protected boolean isStale(AutoScaleEntry entry) {
        try {
            return getConfig().getStaleChecker().isStale(entry.getObject());
        } catch (Exception e) {
            _logger.error("failed to invoke staleChecker, ignore the check", e);
            return false;
        }
    }

    public static class AutoScaleEntry extends DefaultEntry {

        protected interface Status extends DefaultEntry.Status {
            String PENDING_CLOSE = "pending_close";
            String CLOSED = "closed";
        }

        private long _creationTime;
        private volatile long _lastUsedTime;

        protected AutoScaleEntry(Integer index, Object obj) {
            super(index, obj);

            _creationTime = System.currentTimeMillis();
            _lastUsedTime = _creationTime;
        }

        @Override
        protected Integer getNumber() {
            return super.getNumber();
        }

        @Override
        protected String getStatus() {
            return super.getStatus();
        }

        @Override
        protected void setStatus(String status) {
            super.setStatus(status);
        }

        protected long getCreationTime() {
            return _creationTime;
        }

        protected long getLastUsedTime() {
            return _lastUsedTime;
        }

        protected void renew() {
            _lastUsedTime = System.currentTimeMillis();
        }

    }

}
