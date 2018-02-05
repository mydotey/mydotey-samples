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

    private int _size;
    private ReusableEntry[] _reusables;

    private BlockingQueue<Integer> _usedIndexes;
    private BlockingQueue<Integer> _freeIndexes;

    private ReusablePoolConfig _config;

    private Object _acquireLock;

    public ReusablePool(ReusablePoolConfig config) {
        Objects.requireNonNull(config, "config is null");
        _config = config;

        init();
    }

    private void init() {
        _reusables = new ReusableEntry[_config.getMaxSize()];

        _usedIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());
        _freeIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());

        _acquireLock = new Object();
    }

    private Reusable newObject() {
        return new Reusable();
    }

    public ReusableEntry acquire() throws InterruptedException {
        if (_size < _config.getMaxSize()) {
            ReusableEntry entry = tryAcquire();
            if (entry != null)
                return entry;

            synchronized (_acquireLock) {
                if (_size < _config.getMaxSize()) {
                    entry = tryAcquire();
                    if (entry == null) {
                        entry = new ReusableEntry(_size, newObject());
                        _reusables[_size] = entry;
                        _usedIndexes.add(_size);
                        _size++;
                    }

                    return entry;
                }
            }
        }

        Integer index = _freeIndexes.take();
        _usedIndexes.add(index);
        return _reusables[index];
    }

    public ReusableEntry tryAcquire() {
        Integer index = _freeIndexes.poll();
        if (index == null)
            return null;

        _usedIndexes.add(index);
        return _reusables[index];
    }

    public void release(ReusableEntry reusableEntry) {
        if (reusableEntry == null || reusableEntry.released())
            return;

        synchronized (reusableEntry) {
            if (reusableEntry.released())
                return;

            _reusables[reusableEntry.index()] = reusableEntry.clone();
            reusableEntry.markReleased();
        }

        _usedIndexes.remove(reusableEntry.index());
        _freeIndexes.add(reusableEntry.index());
    }

}
