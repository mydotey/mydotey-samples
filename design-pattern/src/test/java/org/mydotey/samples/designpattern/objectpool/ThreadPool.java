package org.mydotey.samples.designpattern.objectpool;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.mydotey.samples.designpattern.objectpool.ObjectPool.Entry;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.autoscale.DefaultAutoScaleObjectPool;
import org.mydotey.samples.designpattern.objectpool.autoscale.DefaultAutoScaleObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class ThreadPool implements Closeable {

    private ObjectPool<WorkerThread> _threadPool;

    public ThreadPool() {
        _threadPool = newObjectPool();
    }

    protected ObjectPoolConfig<WorkerThread> newObjectPoolConfig() {
        return DefaultAutoScaleObjectPoolConfig.<WorkerThread> newBuilder().setMinSize(5).setMaxSize(50)
                .setScaleFactor(5).setCheckInterval(TimeUnit.SECONDS.toMillis(5))
                .setObjectFactory(() -> new WorkerThread(t -> getObjectPool().release(t.getPoolEntry())))
                .setOnCreate(e -> {
                    e.getObject().setPoolEntry(e);
                    e.getObject().start();
                }).setMaxIdleTime(TimeUnit.MINUTES.toMillis(1)).setObjectTtl(TimeUnit.MINUTES.toMillis(5))
                .setOnClose(e -> e.getObject().interrupt())
                .setStaleChecker(t -> t.getState() == Thread.State.TERMINATED).build();

    }

    protected ObjectPool<WorkerThread> newObjectPool() {
        return new DefaultAutoScaleObjectPool<WorkerThread>(
                (AutoScaleObjectPoolConfig<WorkerThread>) newObjectPoolConfig());
    }

    protected ObjectPool<WorkerThread> getObjectPool() {
        return _threadPool;
    }

    public int getSize() {
        return getObjectPool().getSize();
    }

    public void submitTask(Runnable task) throws InterruptedException {
        Objects.requireNonNull(task, "task is null");

        Entry<WorkerThread> entry = getObjectPool().acquire();
        entry.getObject().setTask(task);
    }

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
