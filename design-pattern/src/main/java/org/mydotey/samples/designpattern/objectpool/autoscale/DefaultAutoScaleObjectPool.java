package org.mydotey.samples.designpattern.objectpool.autoscale;

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
public class DefaultAutoScaleObjectPool<T> extends DefaultObjectPool<T> implements AutoScaleObjectPool<T> {

    private static Logger _logger = LoggerFactory.getLogger(DefaultAutoScaleObjectPool.class);

    protected Object _scaleLock;
    protected ScheduledExecutorService _scheduledExecutorService;

    public DefaultAutoScaleObjectPool(AutoScaleObjectPoolConfig<T> config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

        _scaleLock = new Object();
        _scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        _scheduledExecutorService.scheduleWithFixedDelay(() -> DefaultAutoScaleObjectPool.this.autoCheck(),
                getConfig().getCheckInterval(), getConfig().getCheckInterval(), TimeUnit.MILLISECONDS);
    }

    @Override
    protected DefaultEntry<T> tryAddNewEntryAndAcquireOne() {
        synchronized (_scaleLock) {
            DefaultEntry<T> entry = super.tryAddNewEntryAndAcquireOne();
            if (entry != null)
                tryAddNewEntry(getConfig().getScaleFactor() - 1);

            return entry;
        }
    }

    @Override
    protected AutoScaleEntry<T> tryAddNewEntry() {
        AutoScaleEntry<T> entry = (AutoScaleEntry<T>) tryCreateNewEntry();
        if (entry != null) {
            synchronized (entry.getNumber()) {
                _entries.put(entry.getNumber(), entry);
                _availableNumbers.add(entry.getNumber());
            }
        }

        return entry;
    }

    @Override
    protected AutoScaleEntry<T> newPoolEntry(Integer number) {
        return (AutoScaleEntry<T>) super.newPoolEntry(number);
    }

    @Override
    protected AutoScaleEntry<T> newConcretePoolEntry(Integer number) {
        return new AutoScaleEntry<T>(number, newObject());
    }

    @Override
    protected AutoScaleEntry<T> getEntry(Integer number) {
        return (AutoScaleEntry<T>) super.getEntry(number);
    }

    @Override
    public AutoScaleObjectPoolConfig<T> getConfig() {
        return (AutoScaleObjectPoolConfig<T>) super.getConfig();
    }

    @Override
    protected AutoScaleEntry<T> acquire(Integer number) {
        synchronized (number) {
            boolean success = tryRefresh(number);
            AutoScaleEntry<T> entry = (AutoScaleEntry<T>) super.acquire(number);
            if (!success)
                entry.renew();

            return entry;
        }
    }

    @Override
    protected void releaseNumber(Integer number) {
        synchronized (number) {
            AutoScaleEntry<T> entry = getEntry(number);
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
        AutoScaleEntry<T> entry = getEntry(number);
        if (!needScaleIn(entry))
            return false;

        synchronized (number) {
            synchronized (_scaleLock) {
                entry = getEntry(number);
                if (!needScaleIn(entry))
                    return false;

                if (!_availableNumbers.remove(number))
                    return false;

                scaleIn(entry);
                return true;
            }
        }
    }

    protected void scaleIn(AutoScaleEntry<T> entry) {
        _entries.remove(entry.getNumber());
        _numberPool.add(entry.getNumber());
        close(entry);
    }

    protected boolean tryRefresh(Integer number) {
        AutoScaleEntry<T> entry = getEntry(number);
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

    protected boolean tryRefresh(AutoScaleEntry<T> entry) {
        AutoScaleEntry<T> newEntry = null;
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

    protected boolean isExpired(AutoScaleEntry<T> entry) {
        return entry.getCreationTime() + getConfig().getObjectTtl() <= System.currentTimeMillis();
    }

    protected boolean needScaleIn(AutoScaleEntry<T> entry) {
        return entry.getStatus() == AutoScaleEntry.Status.AVAILABLE
                && entry.getLastUsedTime() + getConfig().getMaxIdleTime() <= System.currentTimeMillis()
                && getSize() > getConfig().getMinSize();
    }

    protected boolean isStale(AutoScaleEntry<T> entry) {
        try {
            return getConfig().getStaleChecker().isStale(entry.getObject());
        } catch (Exception e) {
            _logger.error("staleChecker failed, ignore", e);
            return false;
        }
    }

    @Override
    public void doClose() {
        super.doClose();

        try {
            _scheduledExecutorService.shutdown();
        } catch (Exception e) {
            _logger.error("shutdown thread pool failed.", e);
        }
    }

    public static class AutoScaleEntry<T> extends DefaultEntry<T> {

        protected interface Status extends DefaultEntry.Status {
            String PENDING_CLOSE = "pending_close";
        }

        private long _creationTime;
        private volatile long _lastUsedTime;

        protected AutoScaleEntry(Integer index, T obj) {
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
