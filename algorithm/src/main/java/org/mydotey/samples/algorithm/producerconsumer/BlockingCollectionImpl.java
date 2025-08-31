package org.mydotey.samples.algorithm.producerconsumer;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionImpl<T> implements BlockingCollection<T> {

    private Collection<T> _collection;

    private Object _addLock;
    private Object _removeLock;

    public BlockingCollectionImpl(ThreadSafeCollection<T> collection) {
        ObjectExtension.requireNonNull(collection, "collection");

        _collection = collection;
        _addLock = new Object();
        _removeLock = new Object();
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
        synchronized (_addLock) {
            while (_collection.size() == _collection.capacity())
                _addLock.wait();

            _collection.add(e);
        }

        synchronized (_removeLock) {
            _removeLock.notify();
        }
    }

    @Override
    public T remove() throws Exception {
        T e;
        synchronized (_removeLock) {
            while (_collection.size() == 0)
                _removeLock.wait();

            e = _collection.remove();
        }

        synchronized (_addLock) {
            _addLock.notify();
        }
        return e;
    }

}
