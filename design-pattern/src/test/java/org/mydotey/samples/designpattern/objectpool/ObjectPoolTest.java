package org.mydotey.samples.designpattern.objectpool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class ObjectPoolTest {
    
    @Test
    public void threadPoolCreateTest() {
        WorkerThreadPool pool = new WorkerThreadPool();
        Assert.assertNotNull(pool);
    }

}
