package org.mydotey.samples.designpattern.objectpool.threadpool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleThreadPoolConfig extends ThreadPoolConfig {

    long getMaxIdleTime();

    int getScaleFactor();

    long getCheckInterval();

    interface Builder extends ThreadPoolConfig.Builder {

        Builder setMaxIdleTime(long maxIdleTime);

        Builder setScaleFactor(int scaleFactor);

        Builder setCheckInterval(long checkInterval);

        @Override
        AutoScaleThreadPoolConfig build();
    }

}