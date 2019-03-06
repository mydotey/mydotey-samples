package org.mydotey.samples.algorithm.loadbalance;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class OverflowTest {

    @Test
    public void maxAdd() {
        int i = Integer.MAX_VALUE + 1;
        Assert.assertEquals(Integer.MIN_VALUE, i);
        i &= Integer.MAX_VALUE;
        Assert.assertEquals(0, i);
    }

    @Test
    public void maxAdd2() {
        AtomicInteger i = new AtomicInteger(Integer.MAX_VALUE);
        i.incrementAndGet();
        Assert.assertEquals(Integer.MIN_VALUE, i.get());
        Assert.assertEquals(0, i.get() & Integer.MAX_VALUE);
    }

}
