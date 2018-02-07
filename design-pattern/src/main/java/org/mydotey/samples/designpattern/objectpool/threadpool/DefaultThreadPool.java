package org.mydotey.samples.designpattern.objectpool.threadpool;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.ObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.ObjectPool.Entry;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.facade.ObjectPools;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class DefaultThreadPool implements Closeable, ThreadPool {

    private ObjectPool<WorkerThread> _threadPool;

    protected DefaultThreadPool(ThreadPoolConfig.Builder builder) {
        _threadPool = ObjectPools.newObjectPool(newObjectPoolConfig(builder));
    }

    protected ObjectPoolConfig<WorkerThread> newObjectPoolConfig(ThreadPoolConfig.Builder builder) {
        return ((DefaultThreadPoolConfig.Builder) builder).setThreadPool(this).build();
    }

    protected DefaultThreadPool(AutoScaleThreadPoolConfig.Builder builder) {
        _threadPool = ObjectPools.newAutoScaleObjectPool(newAutoScaleObjectPoolConfig(builder));
    }

    protected AutoScaleObjectPoolConfig<WorkerThread> newAutoScaleObjectPoolConfig(
            AutoScaleThreadPoolConfig.Builder builder) {
        return ((DefaultAutoScaleThreadPoolConfig.Builder) builder).setThreadPool(this).build();
    }

    protected ObjectPool<WorkerThread> getObjectPool() {
        return _threadPool;
    }

    @Override
    public int getSize() {
        return getObjectPool().getSize();
    }

    @Override
    public void submitTask(Runnable task) throws InterruptedException {
        Objects.requireNonNull(task, "task is null");

        Entry<WorkerThread> entry = getObjectPool().acquire();
        entry.getObject().setTask(task);
    }

    @Override
    public boolean trySubmitTask(Runnable task) {
        Objects.requireNonNull(task, "task is null");

        Entry<WorkerThread> entry = getObjectPool().tryAcquire();
        if (entry == null)
            return false;

        entry.getObject().setTask(task);
        return true;
    }

    @Override
    public void close() throws IOException {
        getObjectPool().close();
    }
}
