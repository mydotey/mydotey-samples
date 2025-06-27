package org.mydotey.samples.designpattern.iterator;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Iterator<T> {

    boolean hasNext();

    T getNext();

}
