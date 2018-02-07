package org.mydotey.samples.designpattern.objectpool;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class ObjectPoolTest {

    private volatile int counter;
    private CountDownLatch _countDownLatch;

    @Test
    public void threadPoolCreateTest() throws IOException {
        ThreadPool pool = new ThreadPool();
        System.out.println(pool.getSize());
        Assert.assertEquals(5, pool.getSize());
        pool.close();
    }

    @Test
    public void threadPoolSubmitTaskTest() throws IOException, InterruptedException {
        int count = 5;
        _countDownLatch = new CountDownLatch(count);
        Runnable task = () -> {
            counter++;
            _countDownLatch.countDown();
        };
        long now = System.currentTimeMillis();
        try (ThreadPool pool = new ThreadPool()) {
            System.out.println("new thread pool eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("pool size: " + pool.getSize());
            System.out.println("counter value: " + counter);
            Assert.assertEquals(0, counter);

            now = System.currentTimeMillis();
            for (int i = 0; i < count; i++)
                pool.submitTask(task);

            System.out.println("submit tasks eclipsed: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);

            _countDownLatch.await();

            System.out.println("tasks run time: " + (System.currentTimeMillis() - now));
            System.out.println("counter value: " + counter);
            System.out.println("pool size: " + pool.getSize());
            Assert.assertEquals(count, counter);
        }
    }

}
