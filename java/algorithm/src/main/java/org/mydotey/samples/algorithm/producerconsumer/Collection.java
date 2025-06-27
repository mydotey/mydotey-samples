package org.mydotey.samples.algorithm.producerconsumer;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public interface Collection<T> {

    int capacity();

    int size();

    void add(T e) throws Exception;

    T remove() throws Exception;

}
