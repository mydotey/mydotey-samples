package org.mydotey.samples.algorithm.producerconsumer;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionImpl4<T> implements BlockingCollection<T> {
    
    private Collection<T> _collection;

    private Object _lock;

    public BlockingCollectionImpl4(ThreadSafeCollection<T> collection) {
        ObjectExtension.requireNonNull(collection, "collection");

        _collection = collection;
        _lock = new Object();
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
        synchronized (_lock) {
            while (size() == capacity()) {
                _lock.wait();
            }

            _collection.add(e);
            _lock.notifyAll();
        }
    }

    @Override
    public T remove() throws Exception {
        synchronized (_lock) {
            while (size() == 0) {
                _lock.wait();
            }

            T e = _collection.remove();
            _lock.notifyAll();
            return e;
        }
    }

}
