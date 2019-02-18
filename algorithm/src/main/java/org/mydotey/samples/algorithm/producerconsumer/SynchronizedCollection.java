package org.mydotey.samples.algorithm.producerconsumer;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class SynchronizedCollection<T> implements ThreadSafeCollection<T> {

    private Collection<T> _collection;

    public SynchronizedCollection(Collection<T> collection) {
        ObjectExtension.requireNonNull(collection, "collection");
        _collection = collection;
    }

    @Override
    public synchronized int capacity() {
        return _collection.capacity();
    }

    @Override
    public synchronized int size() {
        return _collection.size();
    }

    @Override
    public synchronized void add(T e) throws Exception {
        _collection.add(e);
    }

    @Override
    public synchronized T remove() throws Exception {
        return _collection.remove();
    }

}
