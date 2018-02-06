package org.mydotey.samples.designpattern.objectpool;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class DefaultObjectPool<T> implements ObjectPool<T> {

    protected ConcurrentSkipListSet<Integer> _numberPool;
    protected ConcurrentHashMap<Integer, Entry<T>> _entries;

    protected BlockingQueue<Integer> _availableNumbers;

    protected ObjectPoolConfig<T> _config;

    protected Object _acquireLock;

    public DefaultObjectPool(ObjectPoolConfig<T> config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    protected void init() {
        _numberPool = new ConcurrentSkipListSet<>();
        for (int i = 0; i < _config.getMaxSize(); i++)
            _numberPool.add(new Integer(i));

        _entries = new ConcurrentHashMap<>();
        _availableNumbers = new ArrayBlockingQueue<>(_config.getMaxSize());

        _acquireLock = new Object();

        tryAddNewEntry(_config.getMinSize());
    }

    protected DefaultEntry<T> tryAddNewEntryAndAcquireOne() {
        DefaultEntry<T> entry = tryCreateNewEntry();
        if (entry != null)
            _entries.put(entry.getNumber(), entry);

        return entry;
    }

    protected void tryAddNewEntry(int count) {
        for (int i = 0; i < count; i++)
            tryAddNewEntry();
    }

    protected DefaultEntry<T> tryAddNewEntry() {
        DefaultEntry<T> entry = tryCreateNewEntry();
        if (entry != null) {
            _entries.put(entry.getNumber(), entry);
            _availableNumbers.add(entry.getNumber());
        }

        return entry;
    }

    protected DefaultEntry<T> tryCreateNewEntry() {
        Integer number = _numberPool.pollFirst();
        if (number == null)
            return null;

        DefaultEntry<T> entry = null;
        try {
            entry = newPoolEntry(number);
        } catch (Exception e) {
            _numberPool.add(number);
            throw e;
        }

        entry.setStatus(DefaultEntry.Status.AVAILABLE);
        return entry;
    }

    protected DefaultEntry<T> newPoolEntry(Integer number) {
        DefaultEntry<T> entry = newConcretePoolEntry(number);
        _config.getOnEntryCreate().accept(entry);
        return entry;
    }

    protected DefaultEntry<T> newConcretePoolEntry(Integer number) {
        return new DefaultEntry<T>(number, newObject());
    }

    protected T newObject() {
        T obj = _config.getObjectFactory().get();
        if (obj == null)
            throw new IllegalStateException("got null from the object factory");

        return obj;
    }

    protected DefaultEntry<T> getEntry(Integer number) {
        return (DefaultEntry<T>) _entries.get(number);
    }

    @Override
    public ObjectPoolConfig<T> getConfig() {
        return _config;
    }

    @Override
    public int getSize() {
        return _entries.size();
    }

    @Override
    public DefaultEntry<T> acquire() throws InterruptedException {
        DefaultEntry<T> entry = tryAcquire();
        if (entry != null)
            return entry;

        Integer number = _availableNumbers.take();
        return acquire(number);
    }

    @Override
    public DefaultEntry<T> tryAcquire() {
        if (_numberPool.size() > 0) {
            Integer number = _availableNumbers.poll();
            if (number != null)
                return acquire(number);

            synchronized (_acquireLock) {
                if (_numberPool.size() > 0) {
                    number = _availableNumbers.poll();
                    if (number != null)
                        return acquire(number);

                    DefaultEntry<T> entry = tryAddNewEntryAndAcquireOne();
                    if (entry != null)
                        return acquire(entry);
                }
            }
        }

        return null;
    }

    protected DefaultEntry<T> acquire(Integer number) {
        return acquire(getEntry(number));
    }

    protected DefaultEntry<T> acquire(DefaultEntry<T> entry) {
        entry.setStatus(DefaultEntry.Status.ACQUIRED);
        return entry.clone();
    }

    @Override
    public void release(Entry<T> entry) {
        DefaultEntry<T> defaultEntry = (DefaultEntry<T>) entry;
        if (defaultEntry == null || defaultEntry.getStatus() == DefaultEntry.Status.RELEASED)
            return;

        synchronized (defaultEntry) {
            if (defaultEntry.getStatus() == DefaultEntry.Status.RELEASED)
                return;

            defaultEntry.setStatus(DefaultEntry.Status.RELEASED);
        }

        releaseNumber(defaultEntry.getNumber());
    }

    protected void releaseNumber(Integer number) {
        getEntry(number).setStatus(DefaultEntry.Status.AVAILABLE);
        _availableNumbers.add(number);
    }

    public static class DefaultEntry<T> implements Entry<T>, Cloneable {

        protected interface Status {
            String AVAILABLE = "available";
            String ACQUIRED = "acquired";
            String RELEASED = "released";
        }

        private Integer _number;
        private volatile String _status;

        private T _obj;

        protected DefaultEntry(Integer number, T obj) {
            _number = number;
            _obj = obj;
        }

        protected Integer getNumber() {
            return _number;
        }

        protected String getStatus() {
            return _status;
        }

        protected void setStatus(String status) {
            _status = status;
        }

        @Override
        public T getObject() {
            return _obj;
        }

        @SuppressWarnings("unchecked")
        @Override
        public DefaultEntry<T> clone() {
            try {
                return (DefaultEntry<T>) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException(e);
            }
        }

    }
}
