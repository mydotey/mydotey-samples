package org.mydotey.samples.designpattern.objectpool;

import java.io.Closeable;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPool<T> extends Closeable {

    ObjectPoolConfig<T> getConfig();

    int getSize();

    boolean isClosed();

    Entry<T> acquire() throws InterruptedException;

    Entry<T> tryAcquire();

    void release(Entry<T> entry);

    interface Entry<T> {

        T getObject();

    }
}