package org.mydotey.samples.algorithm.producerconsumer;

import java.util.concurrent.Semaphore;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionImpl3<T> implements BlockingCollection<T> {

    private Collection<T> _collection;

    private Semaphore _addLock;
    private Semaphore _removeLock;

    public BlockingCollectionImpl3(ThreadSafeCollection<T> collection) {
        ObjectExtension.requireNonNull(collection, "collection");

        _collection = collection;
        _addLock = new Semaphore(collection.capacity());
        _removeLock = new Semaphore(0);
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
        _addLock.acquire();
        try {
            _collection.add(e);
        } finally {
            _removeLock.release();
        }
    }

    @Override
    public T remove() throws Exception {
        _removeLock.acquire();
        try {
            return _collection.remove();
        } finally {
            _addLock.release();
        }
    }

}
