package org.mydotey.samples.designpattern.objectpool;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class DefaultObjectPool<T> implements ObjectPool<T> {

    private static Logger _logger = LoggerFactory.getLogger(DefaultObjectPool.class);

    protected volatile boolean _isClosed;
    protected Object _closeLock;

    protected ConcurrentSkipListSet<Integer> _numberPool;
    protected ConcurrentHashMap<Integer, Entry<T>> _entries;

    protected BlockingDeque<Integer> _availableNumbers;

    protected ObjectPoolConfig<T> _config;

    public DefaultObjectPool(ObjectPoolConfig<T> config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    protected void init() {
        _closeLock = new Object();

        _numberPool = new ConcurrentSkipListSet<>();
        for (int i = 0; i < _config.getMaxSize(); i++)
            _numberPool.add(new Integer(i));

        _entries = new ConcurrentHashMap<>(_config.getMaxSize());
        _availableNumbers = new LinkedBlockingDeque<>(_config.getMaxSize());

        tryAddNewEntry(_config.getMinSize());
    }

    protected void tryAddNewEntry(int count) {
        for (int i = 0; i < count; i++)
            tryAddNewEntry();
    }

    protected DefaultEntry<T> tryAddNewEntry() {
        DefaultEntry<T> entry = tryCreateNewEntry();
        if (entry != null) {
            _entries.put(entry.getNumber(), entry);
            _availableNumbers.addFirst(entry.getNumber());
        }

        return entry;
    }

    protected DefaultEntry<T> tryCreateNewEntry() {
        if (isClosed())
            return null;

        Integer number = _numberPool.pollFirst();
        if (number == null)
            return null;

        synchronized (_closeLock) {
            if (isClosed())
                return null;

            DefaultEntry<T> entry = null;
            try {
                entry = newPoolEntry(number);
            } catch (Exception e) {
                _numberPool.add(number);
                _logger.error("failed to new object", e);
                throw e;
            }

            entry.setStatus(DefaultEntry.Status.AVAILABLE);
            return entry;
        }
    }

    protected DefaultEntry<T> newPoolEntry(Integer number) {
        DefaultEntry<T> entry = newConcretePoolEntry(number);
        try {
            _config.getOnCreate().accept(entry);
        } catch (Exception e) {
            _logger.error("onEntryCreate failed", e);
            throw e;
        }

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
    public boolean isClosed() {
        return _isClosed;
    }

    @Override
    public DefaultEntry<T> acquire() throws InterruptedException {
        DefaultEntry<T> entry = tryAcquire();
        if (entry != null)
            return entry;

        Integer number = _availableNumbers.takeFirst();
        return acquire(number);
    }

    @Override
    public DefaultEntry<T> tryAcquire() {
        Integer number = _availableNumbers.pollFirst();
        if (number != null)
            return tryAcquire(number);

        return tryAddNewEntryAndAcquireOne();
    }

    protected DefaultEntry<T> tryAcquire(Integer number) {
        return doAcquire(number);
    }

    protected DefaultEntry<T> acquire(Integer number) throws InterruptedException {
        return doAcquire(number);
    }

    protected DefaultEntry<T> doAcquire(Integer number) {
        DefaultEntry<T> entry = getEntry(number);
        entry.setStatus(DefaultEntry.Status.ACQUIRED);
        return entry.clone();
    }

    protected DefaultEntry<T> tryAddNewEntryAndAcquireOne() {
        DefaultEntry<T> entry = tryCreateNewEntry();
        if (entry == null)
            return null;

        entry.setStatus(DefaultEntry.Status.ACQUIRED);
        _entries.put(entry.getNumber(), entry);
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
        _availableNumbers.addFirst(number);
    }

    @Override
    public void close() throws IOException {
        if (isClosed())
            return;

        synchronized (_closeLock) {
            if (isClosed())
                return;

            _isClosed = true;
            doClose();
        }
    }

    protected void doClose() {
        for (Entry<T> entry : _entries.values()) {
            close((DefaultEntry<T>) entry);
        }
    }

    protected void close(DefaultEntry<T> entry) {
        entry.setStatus(DefaultEntry.Status.CLOSED);

        try {
            _config.getOnClose().accept(entry);
        } catch (Exception e) {
            _logger.error("close object failed", e);
        }
    }

    protected static class DefaultEntry<T> implements Entry<T>, Cloneable {

        protected interface Status {
            String AVAILABLE = "available";
            String ACQUIRED = "acquired";
            String RELEASED = "released";
            String CLOSED = "closed";
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
