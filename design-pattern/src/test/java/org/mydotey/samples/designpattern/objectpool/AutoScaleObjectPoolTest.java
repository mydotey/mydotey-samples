package org.mydotey.samples.designpattern.objectpool;

import org.mydotey.samples.designpattern.objectpool.threadpool.AutoScaleThreadPoolConfig;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPool;
import org.mydotey.samples.designpattern.objectpool.threadpool.ThreadPools;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public class AutoScaleObjectPoolTest extends ObjectPoolTest {

    protected ThreadPool newThreadPool() {
        AutoScaleThreadPoolConfig.Builder builder = ThreadPools.newAutoScaleThreadPoolConfigBuilder();
        builder.setMinSize(_minSize).setMaxSize(_maxSize);
        return ThreadPools.newAutoScaleThreadPool(builder);
    }

}
