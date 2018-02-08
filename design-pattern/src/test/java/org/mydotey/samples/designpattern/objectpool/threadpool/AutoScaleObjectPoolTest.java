package org.mydotey.samples.designpattern.objectpool.threadpool;

import java.util.concurrent.TimeUnit;

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
        AutoScaleThreadPoolConfig.Builder builder = ThreadPools.newAutoScaleThreadPoolConfigBuilder();
        builder.setMinSize(_minSize).setMaxSize(_maxSize).setScaleFactor(5)
                .setCheckInterval(TimeUnit.SECONDS.toMillis(5)).setMaxIdleTime(TimeUnit.SECONDS.toMillis(5));
        return ThreadPools.newAutoScaleThreadPool(builder);
    }

}
