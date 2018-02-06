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
public class DefaultObjectPool implements ObjectPool {

    protected ConcurrentSkipListSet<Integer> _numberPool;
    protected ConcurrentHashMap<Integer, DefaultEntry> _entries;

    protected BlockingQueue<Integer> _availableNumbers;

    protected ObjectPoolConfig _config;

    protected Object _acquireLock;

    public DefaultObjectPool(ObjectPoolConfig config) {
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

    protected DefaultEntry tryAddNewEntryAndAcquireOne() {
        return tryCreateNewEntry();
    }

    protected void tryAddNewEntry(int count) {
        for (int i = 0; i < count; i++)
            tryAddNewEntry();
    }

    protected DefaultEntry tryAddNewEntry() {
        DefaultEntry entry = tryCreateNewEntry();
        if (entry != null)
            _availableNumbers.add(entry.getNumber());

        return entry;
    }

    protected DefaultEntry tryCreateNewEntry() {
        Integer number = _numberPool.pollFirst();
        if (number == null)
            return null;

        DefaultEntry entry = null;
        try {
            entry = newPoolEntry(number);
        } catch (Exception e) {
            _numberPool.add(number);
            throw e;
        }

        entry.setStatus(DefaultEntry.Status.AVAILABLE);
        _entries.put(number, entry);
        return entry;
    }

    protected DefaultEntry newPoolEntry(Integer number) {
        return new DefaultEntry(number, newObject());
    }

    protected Object newObject() {
        Object obj = _config.getObjectFactory().get();
        if (obj == null)
            throw new IllegalStateException("got null from the object factory");

        return obj;
    }

    protected DefaultEntry getEntry(Integer number) {
        return _entries.get(number);
    }

    @Override
    public ObjectPoolConfig getConfig() {
        return _config;
    }

    @Override
    public int getSize() {
        return _entries.size();
    }

    @Override
    public DefaultEntry acquire() throws InterruptedException {
        DefaultEntry entry = tryAcquire();
        if (entry != null)
            return entry;

        Integer number = _availableNumbers.take();
        return acquire(number);
    }

    @Override
    public DefaultEntry tryAcquire() {
        if (_numberPool.size() > 0) {
            Integer number = _availableNumbers.poll();
            if (number != null)
                return acquire(number);

            synchronized (_acquireLock) {
                if (_numberPool.size() > 0) {
                    number = _availableNumbers.poll();
                    if (number != null)
                        return acquire(number);

                    DefaultEntry entry = tryAddNewEntryAndAcquireOne();
                    if (entry != null)
                        return acquire(entry);
                }
            }
        }

        return null;
    }

    protected DefaultEntry acquire(Integer number) {
        return acquire(getEntry(number));
    }

    protected DefaultEntry acquire(DefaultEntry entry) {
        entry.setStatus(DefaultEntry.Status.ACQUIRED);
        return entry.clone();
    }

    @Override
    public void release(Entry entry) {
        DefaultEntry defaultEntry = (DefaultEntry) entry;
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

    public static class DefaultEntry implements Entry, Cloneable {

        protected interface Status {
            String AVAILABLE = "available";
            String ACQUIRED = "acquired";
            String RELEASED = "released";
        }

        private Integer _number;
        private volatile String _status;

        private Object _obj;

        protected DefaultEntry(Integer number, Object obj) {
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
        public Object getObject() {
            return _obj;
        }

        @Override
        public DefaultEntry clone() {
            try {
                return (DefaultEntry) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException(e);
            }
        }

    }
}
