package org.mydotey.samples.algorithm.producerconsumer;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class TestCollection<T> implements Collection<T> {

    private Queue<T> _data;

    private int _capacity;
    private AtomicInteger _size;

    public TestCollection(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity <= 0");

        _capacity = capacity;
        _size = new AtomicInteger();
        _data = new ArrayDeque<>(capacity);
    }

    @Override
    public int capacity() {
        return _capacity;
    }

    @Override
    public int size() {
        return _size.get();
    }

    @Override
    public void add(T e) throws Exception {
        if (size() == _capacity)
            throw new IllegalArgumentException("collection is full");

        _size.incrementAndGet();
        _data.add(e);
    }

    @Override
    public T remove() throws Exception {
        if (size() == 0)
            throw new IllegalArgumentException("collection is empty");

        _size.decrementAndGet();
        return _data.remove();
    }

}
