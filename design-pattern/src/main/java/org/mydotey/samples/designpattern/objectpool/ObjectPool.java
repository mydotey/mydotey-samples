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

    private int _size;
    private ObjectPoolEntry[] _reusables;

    private Set<Integer> _usedIndexes;
    private BlockingQueue<Integer> _freeIndexes;

    private ObjectPoolConfig _config;

    private Object _acquireLock;

    public ObjectPool(ObjectPoolConfig config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    protected void init() {
        _reusables = new ObjectPoolEntry[_config.getMaxSize()];

        _usedIndexes = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>(_config.getMaxSize()));
        _freeIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());

        _acquireLock = new Object();

        scaleOut(_config.getMinSize());
    }

    private void scaleOut(int count) {
        for (int i = 0; i < count && _size < _config.getMaxSize(); i++) {
            Object reusable = _config.getReusableFactory().get();
            if (reusable == null)
                throw new IllegalStateException("Got null from the reusable suppiler.");

            _reusables[_size] = new ObjectPoolEntry(_size, reusable);
            _freeIndexes.add(_size);
            _size++;
        }
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
        _usedIndexes.add(index);
        return _reusables[index];
    }

    public ObjectPoolEntry tryAcquire() {
        Integer index = _freeIndexes.poll();
        if (index == null)
            return null;

        _usedIndexes.add(index);
        return _reusables[index];
    }

    public void release(ObjectPoolEntry reusableEntry) {
        if (reusableEntry == null || reusableEntry.isReleased())
            return;

        synchronized (reusableEntry) {
            if (reusableEntry.isReleased())
                return;

            _reusables[reusableEntry.getIndex()] = reusableEntry.clone();
            reusableEntry.setReleased();
        }

        _usedIndexes.remove(reusableEntry.getIndex());
        _freeIndexes.add(reusableEntry.getIndex());
    }

}
