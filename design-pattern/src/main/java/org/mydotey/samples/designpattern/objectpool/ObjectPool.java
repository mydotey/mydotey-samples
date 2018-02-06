package org.mydotey.samples.designpattern.objectpool;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPool {

    protected int _size;
    protected ObjectPoolEntry[] _entries;

    protected Set<Integer> _usedIndexes;
    protected BlockingQueue<Integer> _freeIndexes;

    protected ObjectPoolConfig _config;

    protected Object _acquireLock;

    public ObjectPool(ObjectPoolConfig config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    protected void init() {
        _entries = new ObjectPoolEntry[_config.getMaxSize()];

        _usedIndexes = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>(_config.getMaxSize()));
        _freeIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());

        _acquireLock = new Object();

        scaleOut(_config.getMinSize());
    }

    protected void scaleOut(int count) {
        for (int i = 0; i < count && _size < _config.getMaxSize(); i++) {
            Object obj = _config.getObjectFactory().get();
            if (obj == null)
                throw new IllegalStateException("Got null from the object suppiler.");

            _entries[_size] = newPoolEntry(new Integer(_size));
            _freeIndexes.add(_entries[_size].getIndex());
            _size++;
        }
    }

    protected Object newObject() {
        Object obj = _config.getObjectFactory().get();
        if (obj == null)
            throw new IllegalStateException("Got null from the object suppiler.");

        return obj;
    }

    protected ObjectPoolEntry newPoolEntry(Integer index) {
        return new ObjectPoolEntry(index, newObject());
    }

    public ObjectPoolConfig getConfig() {
        return _config;
    }

    public int getSize() {
        return _size;
    }

    public ObjectPoolEntry acquire() throws InterruptedException {
        if (_size < _config.getMaxSize()) {
            ObjectPoolEntry entry = tryAcquire();
            if (entry != null)
                return entry;

            synchronized (_acquireLock) {
                if (_size < _config.getMaxSize()) {
                    entry = tryAcquire();
                    if (entry != null)
                        return entry;

                    scaleOut(_config.getScaleFactor());
                }
            }
        }

        Integer index = _freeIndexes.take();
        return acquire(index);
    }

    public ObjectPoolEntry tryAcquire() {
        Integer index = _freeIndexes.poll();
        if (index == null)
            return null;

        return acquire(index);
    }

    protected ObjectPoolEntry acquire(Integer index) {
        _usedIndexes.add(index);
        return _entries[index];
    }

    public void release(ObjectPoolEntry entry) {
        if (entry == null || entry.isReleased())
            return;

        synchronized (entry) {
            if (entry.isReleased())
                return;

            entry.setReleased();
        }

        resetEntry(entry);
    }

    protected void resetEntry(ObjectPoolEntry entry) {
        _entries[entry.getIndex()] = entry.clone();
        _usedIndexes.remove(entry.getIndex());
        _freeIndexes.add(entry.getIndex());
    }

}
