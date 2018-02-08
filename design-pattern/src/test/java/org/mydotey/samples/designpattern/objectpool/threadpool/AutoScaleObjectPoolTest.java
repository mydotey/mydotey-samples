package org.mydotey.samples.designpattern.objectpool.threadpool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.threadpool.AutoScaleThreadPoolConfig;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPool;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPools;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class AutoScaleObjectPoolTest extends ObjectPoolTest {

    @Override
    protected ThreadPool newThreadPool() {
        return newThreadPool(TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(2), TimeUnit.SECONDS.toMillis(10));
    }

    @SuppressWarnings("unchecked")
    protected ThreadPool newThreadPool(long checkInterval, long maxIdleTime, long ttl) {
        AutoScaleThreadPoolConfig.Builder builder = ThreadPools.newAutoScaleThreadPoolConfigBuilder();
        builder.setMinSize(_minSize).setMaxSize(_maxSize).setScaleFactor(5).setCheckInterval(checkInterval)
                .setMaxIdleTime(maxIdleTime);
        ((AutoScaleObjectPoolConfig.Builder<WorkerThread>) builder).setObjectTtl(ttl);
        return ThreadPools.newAutoScaleThreadPool(builder);
    }

    @Override
    public void threadPoolSubmitTaskTest4() throws IOException, InterruptedException {
        int taskCount = _maxSize;
        long taskSleep = 500;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 5000;
        int finalSize = _minSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Override
    public void threadPoolSubmitTaskTest6() throws IOException, InterruptedException {
        int taskCount = 200;
        long taskSleep = 2000;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 10000;
        int finalSize = _minSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Test
    public void threadPoolSubmitTaskTest7() throws IOException, InterruptedException {
        int taskCount = 200;
        long taskSleep = 2000;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 20000;
        int finalSize = _maxSize;
        long checkInterval = 2000;
        long maxIdleTime = 10000;
        long ttl = 5000;
        ThreadPool pool = newThreadPool(checkInterval, maxIdleTime, ttl);
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize, pool);
    }

    @Test
    public void threadPoolSubmitTaskTest8() throws IOException, InterruptedException {
        int taskCount = 200;
        long taskSleep = 2000;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 10000;
        int finalSize = _maxSize;
        long checkInterval = 2000;
        long maxIdleTime = Long.MAX_VALUE;
        long ttl = 10000;
        ThreadPool pool = newThreadPool(checkInterval, maxIdleTime, ttl);
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize, pool);
    }

    @Test
    public void threadPoolSubmitTaskTest9() throws IOException, InterruptedException {
        int taskCount = 200;
        long taskSleep = 2000;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 20000;
        int finalSize = _minSize;
        long checkInterval = 2000;
        long maxIdleTime = 10000;
        long ttl = Long.MAX_VALUE;
        ThreadPool pool = newThreadPool(checkInterval, maxIdleTime, ttl);
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize, pool);
    }

}
