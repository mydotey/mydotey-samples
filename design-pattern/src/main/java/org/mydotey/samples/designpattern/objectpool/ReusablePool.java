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
public class ReusablePool {

    private int _size;
    private ReusableEntry[] _reusables;

    private Set<Integer> _usedIndexes;
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

        _usedIndexes = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>(_config.getMaxSize()));
        _freeIndexes = new ArrayBlockingQueue<>(_config.getMaxSize());

        _acquireLock = new Object();
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
                        for (int i = 0; i < _config.getScaleFactor() && _size < _config.getMaxSize(); i++) {
                            _reusables[_size] = new ReusableEntry(_size, _config.getReusableFactory().get());
                            _freeIndexes.add(_size);
                            _size++;
                        }
                    }
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
