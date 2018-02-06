package org.mydotey.samples.designpattern.objectpool.autoscale;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public interface StaleChecker<T> {

    @SuppressWarnings("rawtypes")
    static StaleChecker DEFAULT = obj -> false;

    boolean isStale(T obj);

}
