package org.mydotey.samples.designpattern.objectpool;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class ObjectPoolTest {

    @Test
    public void threadPoolCreateTest() throws IOException {
        ThreadPool pool = new ThreadPool();
        System.out.println(pool.getSize());
        Assert.assertEquals(5, pool.getSize());
        pool.close();
    }

    @Test
    public void threadPoolSubmitTaskTest() throws IOException, InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Runnable task = () -> {
            counter.incrementAndGet();
            countDownLatch.countDown();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        };
        long now = System.currentTimeMillis();
        try (ThreadPool pool = new ThreadPool()) {
            System.out.println("new thread pool eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("pool size: " + pool.getSize());
            System.out.println("counter value: " + counter);
            Assert.assertEquals(0, counter.get());

            now = System.currentTimeMillis();
            for (int i = 0; i < count; i++)
                pool.submitTask(task);

            System.out.println("pool size: " + pool.getSize());
            System.out.println("submit tasks eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleWithFixedDelay(() -> {
                System.out.println("counter value: " + counter);
                System.out.println("pool size: " + pool.getSize());
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 1000, 100, TimeUnit.MILLISECONDS);
            countDownLatch.await();

            System.out.println("tasks run time: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);
            System.out.println("pool size: " + pool.getSize());
            Assert.assertEquals(count, counter.get());
        }
    }

}
