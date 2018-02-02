package org.mydotey.samples.designpattern.objectpool;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ReusablePool {

    private ReusableEntry[] _reusables;
    private int _size;

    private BlockingQueue<Integer> _usedIndexes;
    private BlockingQueue<Integer> _freeIndexes;

    private ReusablePoolConfig _config;

    public ReusablePool(ReusablePoolConfig config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    private void init() {
        _reusables = new ReusableEntry[_config.getMaxSize()];

        _usedIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());
        _freeIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());
    }

    private Reusable newObject() {
        return new Reusable();
    }

    public synchronized ReusableEntry acquire() throws InterruptedException {
        if (_size == _config.getMaxSize()) {
            Integer index = _freeIndexes.take();
            _usedIndexes.add(index);
            return _reusables[index];
        }

        ReusableEntry entry = tryAcquire();
        if (entry != null)
            return entry;

        entry = new ReusableEntry(_size, newObject());
        _reusables[_size] = entry;
        _usedIndexes.add(_size);
        _size++;

        return entry;
    }

    public ReusableEntry tryAcquire() {
        Integer index = _freeIndexes.poll();
        if (index == null)
            return null;

        synchronized (this) {
            _usedIndexes.add(index);
        }

        return _reusables[index];
    }

    public synchronized void release(ReusableEntry reusableEntry) {
        _usedIndexes.remove(reusableEntry.getIndex());
        _freeIndexes.add(reusableEntry.getIndex());
    }

}
