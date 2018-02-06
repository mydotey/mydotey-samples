package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPool<T> {

    ObjectPoolConfig<T> getConfig();

    int getSize();

    Entry<T> acquire() throws InterruptedException;

    Entry<T> tryAcquire();

    void release(Entry<T> entry);

    interface Entry<T> {

        T getObject();

    }
}