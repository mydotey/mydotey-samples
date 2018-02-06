package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPool {

    ObjectPoolConfig getConfig();

    int getSize();

    Entry acquire() throws InterruptedException;

    Entry tryAcquire();

    void release(Entry entry);

    interface Entry {

        Object getObject();

    }
}