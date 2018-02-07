package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
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
    protected ScheduledExecutorService _checkScheduler;
    protected ExecutorService _releaseExecutor;

    public DefaultAutoScaleObjectPool(AutoScaleObjectPoolConfig<T> config) {
        super(config);
    }

    @Override
    protected void init() {
        super.init();

        _scaleLock = new Object();
        _checkScheduler = Executors.newSingleThreadScheduledExecutor();
        _checkScheduler.scheduleWithFixedDelay(() -> DefaultAutoScaleObjectPool.this.autoCheck(),
                getConfig().getCheckInterval(), getConfig().getCheckInterval(), TimeUnit.MILLISECONDS);

        _releaseExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected BlockingQueue<Integer> newBlockingQueue() {
        return new LinkedBlockingQueue<>(_config.getMaxSize());
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
    protected AutoScaleEntry<T> tryAcquire(Integer number) {
        AutoScaleEntry<T> entry = doAcquire(number);
        if (entry != null)
            return entry;

        return (AutoScaleEntry<T>) tryAcquire();
    }

    @Override
    protected AutoScaleEntry<T> acquire(Integer number) throws InterruptedException {
        AutoScaleEntry<T> entry = doAcquire(number);
        if (entry != null)
            return entry;

        return (AutoScaleEntry<T>) acquire();
    }

    @Override
    protected AutoScaleEntry<T> doAcquire(Integer number) {
        AutoScaleEntry<T> entry;
        synchronized (number) {
            entry = (AutoScaleEntry<T>) super.doAcquire(number);
            if (!needRefresh(entry)) {
                entry.renew();
                return entry;
            } else {
                entry.setStatus(AutoScaleEntry.Status.PENDING_REFRESH);
            }
        }

        _releaseExecutor.submit(() -> doReleaseNumber(number));
        return null;
    }

    @Override
    protected void releaseNumber(Integer number) {
        _releaseExecutor.submit(() -> doReleaseNumber(number));
    }

    protected void doReleaseNumber(Integer number) {
        synchronized (number) {
            AutoScaleEntry<T> entry = getEntry(number);
            if (entry.getStatus() == AutoScaleEntry.Status.PENDING_REFRESH) {
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
        synchronized (_scaleLock) {
            _entries.remove(entry.getNumber());
            _numberPool.add(entry.getNumber());
            close(entry);
        }
    }

    protected boolean tryRefresh(Integer number) {
        AutoScaleEntry<T> entry = getEntry(number);
        if (!needRefresh(entry))
            return false;

        synchronized (number) {
            entry = getEntry(number);
            if (!needRefresh(entry))
                return false;

            if (entry.getStatus() == AutoScaleEntry.Status.AVAILABLE)
                return tryRefresh(entry);

            entry.setStatus(AutoScaleEntry.Status.PENDING_REFRESH);
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

    protected boolean needRefresh(AutoScaleEntry<T> entry) {
        return isExpired(entry) || isStale(entry);
    }

    protected boolean isExpired(AutoScaleEntry<T> entry) {
        return entry.getCreationTime() + getConfig().getObjectTtl() <= System.currentTimeMillis();
    }

    protected boolean isStale(AutoScaleEntry<T> entry) {
        try {
            return getConfig().getStaleChecker().apply(entry.getObject());
        } catch (Exception e) {
            _logger.error("staleChecker failed, ignore", e);
            return false;
        }
    }

    protected boolean needScaleIn(AutoScaleEntry<T> entry) {
        return entry.getStatus() == AutoScaleEntry.Status.AVAILABLE
                && entry.getLastUsedTime() + getConfig().getMaxIdleTime() <= System.currentTimeMillis()
                && getSize() > getConfig().getMinSize();
    }

    @Override
    public void doClose() {
        super.doClose();

        try {
            _checkScheduler.shutdown();
            _releaseExecutor.shutdown();
        } catch (Exception e) {
            _logger.error("shutdown thread pool failed.", e);
        }
    }

    protected static class AutoScaleEntry<T> extends DefaultEntry<T> {

        protected interface Status extends DefaultEntry.Status {
            String PENDING_REFRESH = "pending_refresh";
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
