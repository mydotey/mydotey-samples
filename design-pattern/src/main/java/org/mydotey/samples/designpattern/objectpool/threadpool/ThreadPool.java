package org.mydotey.samples.designpattern.objectpool.threadpool;

import java.io.Closeable;

/**
 * @author koqizhao
 *
 * Feb 8, 2018
 */
public interface ThreadPool extends Closeable {

    int getSize();

    void submitTask(Runnable task) throws InterruptedException;

    boolean trySubmitTask(Runnable task);

}