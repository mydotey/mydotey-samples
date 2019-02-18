package org.mydotey.samples.algorithm.producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionImpl2<T> implements BlockingCollection<T> {

    private Collection<T> _collection;

    private ReentrantLock _lock;
    private Condition _addCondition;
    private Condition _removeCondition;

    public BlockingCollectionImpl2(ThreadSafeCollection<T> collection) {
        ObjectExtension.requireNonNull(collection, "collection");

        _collection = collection;

        _lock = new ReentrantLock();
        _addCondition = _lock.newCondition();
        _removeCondition = _lock.newCondition();
    }

    @Override
    public int size() {
        return _collection.size();
    }

    @Override
    public int capacity() {
        return _collection.capacity();
    }

    @Override
    public void add(T e) throws Exception {
        _lock.lock();
        try {
            while (_collection.size() == _collection.capacity())
                _addCondition.await();

            _collection.add(e);
            _removeCondition.signal();
        } finally {
            _lock.unlock();
        }
    }

    @Override
    public T remove() throws Exception {
        _lock.lock();
        try {
            while (_collection.size() == 0)
                _removeCondition.await();

            T e = _collection.remove();
            _addCondition.signal();
            return e;
        } finally {
            _lock.unlock();
        }
    }

}
