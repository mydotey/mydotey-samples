package org.mydotey.samples.designpattern.objectpool.threadpool;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.mydotey.samples.designpattern.objectpool.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPool;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPoolConfig;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPools;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class ObjectPoolTest {

    protected int _minSize = 10;
    protected int _maxSize = 100;

    protected int _defaultTaskCount = _minSize;
    protected long _defaultTaskSleep = 0;
    protected long _defaultViInitDelay = 1000;
    protected int _defaultSizeAfterSumit = -1;
    protected long _defaultFinishSleep = 0;
    protected int _defaultFinalSize = -1;

    protected ThreadPool newThreadPool() {
        ThreadPoolConfig.Builder builder = ThreadPools.newThreadPoolConfigBuilder();
        builder.setMinSize(_minSize).setMaxSize(_maxSize);
        return ThreadPools.newThreadPool(builder);
    }

    @Test
    public void threadPoolCreateTest() throws IOException {
        System.out.println();
        System.out.println();

        ThreadPool pool = newThreadPool();
        System.out.println("Pool Size: " + pool.getSize());
        Assert.assertEquals(_minSize, pool.getSize());
        pool.close();
    }

    @Test
    public void threadPoolSubmitTaskTest() throws IOException, InterruptedException {
        int taskCount = _minSize;
        long taskSleep = _defaultTaskSleep;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _minSize;
        long finishSleep = 1;
        int finalSize = _defaultFinalSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Test
    public void threadPoolSubmitTaskTest2() throws IOException, InterruptedException {
        int taskCount = _minSize;
        long taskSleep = 10;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _minSize;
        long finishSleep = 1000;
        int finalSize = _minSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Test
    public void threadPoolSubmitTaskTest3() throws IOException, InterruptedException {
        int taskCount = 50;
        long taskSleep = 50;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _defaultSizeAfterSumit;
        long finishSleep = 1000;
        int finalSize = _defaultFinalSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Test
    public void threadPoolSubmitTaskTest4() throws IOException, InterruptedException {
        int taskCount = _maxSize;
        long taskSleep = 500;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 1000;
        int finalSize = _maxSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    @Test
    public void threadPoolSubmitTaskTest5() throws IOException, InterruptedException {
        int taskCount = 200;
        long taskSleep = 2000;
        long viInitDelay = _defaultViInitDelay;
        int sizeAfterSubmit = _maxSize;
        long finishSleep = 1000;
        int finalSize = _maxSize;
        threadPoolSubmitTaskTest(taskCount, taskSleep, viInitDelay, sizeAfterSubmit, finishSleep, finalSize);
    }

    protected void threadPoolSubmitTaskTest(int taskCount, long taskSleep, long viInitDelay, int sizeAfterSubmit,
            long finishSleep, int finalSize) throws IOException, InterruptedException {
        System.out.println();
        System.out.println();

        AtomicInteger counter = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(taskCount);
        Runnable task = () -> {
            counter.incrementAndGet();
            countDownLatch.countDown();
            if (taskSleep <= 0)
                return;

            try {
                Thread.sleep(taskSleep);
            } catch (Exception e) {
            }
        };
        long now = System.currentTimeMillis();
        ScheduledExecutorService executorService = null;
        try (ThreadPool pool = newThreadPool()) {
            System.out.println("new thread pool eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);
            vi(pool);
            Assert.assertEquals(0, counter.get());

            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleWithFixedDelay(() -> {
                System.out.println("counter value: " + counter);
                vi(pool);
            }, 1000, 500, TimeUnit.MILLISECONDS);

            now = System.currentTimeMillis();
            for (int i = 0; i < taskCount; i++)
                pool.submitTask(task);

            System.out.println("submit tasks eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);
            vi(pool);

            Assert.assertTrue(_minSize <= pool.getSize());
            Assert.assertTrue(_maxSize >= pool.getSize());
            if (sizeAfterSubmit >= 0)
                Assert.assertEquals(sizeAfterSubmit, pool.getSize());

            countDownLatch.await();

            System.out.println("tasks run time: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);
            vi(pool);

            Assert.assertEquals(taskCount, counter.get());
            Assert.assertTrue(_minSize <= pool.getSize());
            Assert.assertTrue(_maxSize >= pool.getSize());

            if (finishSleep <= 0)
                return;

            Thread.sleep(finishSleep);

            vi(pool);

            Assert.assertEquals(taskCount, counter.get());
            Assert.assertTrue(_minSize <= pool.getSize());
            Assert.assertTrue(_maxSize >= pool.getSize());

            if (finalSize >= 0)
                Assert.assertEquals(finalSize, pool.getSize());
        } finally {
            if (executorService != null)
                executorService.shutdown();
        }
    }

    protected void vi(ThreadPool pool) {
        System.out.println();
        ObjectPool<?> objectPool = ((DefaultThreadPool) pool).getObjectPool();
        System.out.println("pool size: " + objectPool.getSize());
        System.out.println("pool acquired size: " + objectPool.getAcquiredSize());
        System.out.println("pool available size: " + objectPool.getAvailableSize());
        System.out.println();
    }

}
