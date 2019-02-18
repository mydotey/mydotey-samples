package org.mydotey.samples.algorithm.producerconsumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionTest {

    private BlockingCollection<String> _collection;

    protected BlockingCollection<String> newBlockingCollection(Collection<String> collection) {
        return new BlockingCollectionImpl<>(new SynchronizedCollection<>(collection));
    }

    protected int getCollectionCapacity() {
        return 100;
    }

    @Before
    public void setUp() {
        TestCollection<String> collection = new TestCollection<>(getCollectionCapacity());
        _collection = newBlockingCollection(collection);
    }

    @Test
    public void queueFullBlock() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < getCollectionCapacity() + 1; i++)
                try {
                    _collection.add(String.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
        });
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(100);

        assertBlockState(thread);
        thread.interrupt();
    }

    @Test
    public void queueEmptyBlock() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                _collection.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(100);

        assertBlockState(thread);
        thread.interrupt();
    }

    @Test
    public void produceConsume() throws InterruptedException {
        produceConsume(1, 2);
    }

    @Test
    public void produceConsume2() throws InterruptedException {
        produceConsume(2, 1);
    }

    protected void produceConsume(int produceInterval, int consumeInterval) throws InterruptedException {
        int totalDataSize = 1000;
        CountDownLatch latch = new CountDownLatch(totalDataSize);
        AtomicBoolean failed = new AtomicBoolean();

        Thread thread = new Thread(() -> {
            for (int i = 0; i < totalDataSize; i++)
                try {
                    _collection.add(String.valueOf(i));
                    Thread.sleep(produceInterval);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failed.set(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
        });
        thread.setDaemon(true);
        thread.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < totalDataSize; i++)
                try {
                    _collection.remove();
                    latch.countDown();
                    Thread.sleep(consumeInterval);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failed.set(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
        thread2.setDaemon(true);
        thread2.start();

        boolean success = latch.await(5, TimeUnit.SECONDS);
        Assert.assertTrue(success);
        Assert.assertFalse(failed.get());
    }

    protected void assertBlockState(Thread thread) {
        Assert.assertEquals(Thread.State.WAITING, thread.getState());
    }

}
