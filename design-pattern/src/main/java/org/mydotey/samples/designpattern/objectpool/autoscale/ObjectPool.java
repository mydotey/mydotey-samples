package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.mydotey.samples.designpattern.objectpool.autoscale.ObjectPoolEntry.Status;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPool {

    protected ConcurrentSkipListSet<Integer> _numberPool;
    protected ConcurrentHashMap<Integer, ObjectPoolEntry> _entries;

    protected BlockingQueue<Integer> _availableNumbers;

    protected ObjectPoolConfig _config;

    protected Object _acquireLock;

    public ObjectPool(ObjectPoolConfig config) {
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

    protected ObjectPoolEntry tryAddNewEntryAndAcquireOne() {
        return tryCreateNewEntry();
    }

    protected void tryAddNewEntry(int count) {
        for (int i = 0; i < count; i++)
            tryAddNewEntry();
    }

    protected ObjectPoolEntry tryAddNewEntry() {
        ObjectPoolEntry entry = tryCreateNewEntry();
        if (entry != null)
            _availableNumbers.add(entry.getNumber());

        return entry;
    }

    protected ObjectPoolEntry tryCreateNewEntry() {
        Integer number = _numberPool.pollFirst();
        if (number == null)
            return null;

        ObjectPoolEntry entry = newPoolEntry(number);
        entry.setStatus(Status.AVAILABLE);
        _entries.put(number, entry);
        return entry;
    }

    protected ObjectPoolEntry newPoolEntry(Integer number) {
        return new ObjectPoolEntry(number, newObject());
    }

    protected Object newObject() {
        Object obj = _config.getObjectFactory().get();
        if (obj == null)
            throw new IllegalStateException("Got null from the object suppiler.");

        return obj;
    }

    protected ObjectPoolEntry getEntry(Integer number) {
        return _entries.get(number);
    }

    public ObjectPoolConfig getConfig() {
        return _config;
    }

    public int getSize() {
        return _entries.size();
    }

    public ObjectPoolEntry acquire() throws InterruptedException {
        ObjectPoolEntry entry = tryAcquire();
        if (entry != null)
            return entry;

        Integer number = _availableNumbers.take();
        return acquire(number);
    }

    public ObjectPoolEntry tryAcquire() {
        if (_numberPool.size() > 0) {
            Integer number = _availableNumbers.poll();
            if (number != null)
                return acquire(number);

            synchronized (_acquireLock) {
                if (_numberPool.size() > 0) {
                    number = _availableNumbers.poll();
                    if (number != null)
                        return acquire(number);

                    ObjectPoolEntry entry = tryAddNewEntryAndAcquireOne();
                    if (entry != null)
                        return acquire(entry);
                }
            }
        }

        return null;
    }

    protected ObjectPoolEntry acquire(Integer number) {
        return acquire(getEntry(number));
    }

    protected ObjectPoolEntry acquire(ObjectPoolEntry entry) {
        entry.setStatus(Status.ACQUIRED);
        return entry.clone();
    }

    public void release(ObjectPoolEntry entry) {
        if (entry == null || entry.getStatus() == Status.RELEASED)
            return;

        synchronized (entry) {
            if (entry.getStatus() == Status.RELEASED)
                return;

            entry.setStatus(Status.RELEASED);
        }

        releaseNumber(entry.getNumber());
    }

    protected void releaseNumber(Integer number) {
        getEntry(number).setStatus(Status.AVAILABLE);
        _availableNumbers.add(number);
    }

}
