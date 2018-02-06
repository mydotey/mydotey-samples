package org.mydotey.samples.designpattern.objectpool;

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
public class WorkerThreadPool {

    private ObjectPool<WorkerThread> _threadPool;

    public WorkerThreadPool() {
        _threadPool = newObjectPool();
    }

    protected ObjectPoolConfig<WorkerThread> newObjectPoolConfig() {
        return DefaultAutoScaleObjectPoolConfig.<WorkerThread> newBuilder().setMinSize(5).setMaxSize(50)
                .setObjectFactory(() -> new WorkerThread(t -> getObjectPool().release(t.getPoolEntry())))
                .setOnEntryCreate(e -> {
                    e.getObject().setPoolEntry(e);
                    e.getObject().start();
                }).setMaxIdleTime(TimeUnit.MINUTES.toMillis(1)).setObjectTtl(TimeUnit.MINUTES.toMillis(5))
                .setScaleFactor(5).setCheckInterval(TimeUnit.SECONDS.toMillis(5))
                .setStaleChecker(t -> t.getState() == Thread.State.TERMINATED).build();
    }

    protected ObjectPool<WorkerThread> newObjectPool() {
        return new DefaultAutoScaleObjectPool<WorkerThread>(
                (AutoScaleObjectPoolConfig<WorkerThread>) newObjectPoolConfig());
    }

    protected ObjectPool<WorkerThread> getObjectPool() {
        return _threadPool;
    }

    public void runAsync(Runnable task) {
        try {
            Entry<WorkerThread> entry = getObjectPool().acquire();
            entry.getObject().runAsync(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
