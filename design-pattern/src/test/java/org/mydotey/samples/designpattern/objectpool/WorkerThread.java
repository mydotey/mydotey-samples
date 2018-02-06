package org.mydotey.samples.designpattern.objectpool;

import java.util.Objects;
import java.util.function.Consumer;

import org.mydotey.samples.designpattern.objectpool.ObjectPool.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class WorkerThread extends Thread {

    private static Logger _logger = LoggerFactory.getLogger(WorkerThread.class);

    private volatile Runnable _task;

    private Entry<WorkerThread> _poolEntry;
    private Consumer<WorkerThread> _onTaskComplete;

    private Object _lock = new Object();

    protected WorkerThread(Consumer<WorkerThread> onTaskComplete) {
        Objects.requireNonNull(onTaskComplete, "onTaskComplete is null");
        _onTaskComplete = onTaskComplete;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                _lock.wait();
                try {
                    _task.run();
                } finally {
                    _onTaskComplete.accept(this);
                }
            } catch (Exception e) {
                _logger.error("task threw exception: " + this.getId(), e);
                break;
            }
        }
    }

    protected void runAsync(Runnable task) {
        Objects.requireNonNull(task, "task is null");
        _task = task;
        _lock.notify();
    }

    protected void setPoolEntry(Entry<WorkerThread> poolEntry) {
        _poolEntry = poolEntry;
    }

    protected Entry<WorkerThread> getPoolEntry() {
        return _poolEntry;
    }
}
