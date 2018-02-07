package org.mydotey.samples.designpattern.objectpool;

import java.io.IOException;

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

}
