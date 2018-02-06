package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleObjectPoolConfig extends ObjectPoolConfig {

    long getObjectTtl();

    long getMaxIdleTime();

    StaleChecker getStaleChecker();

    long getStaleCheckInterval();

    int getScaleFactor();

    interface Builder extends ObjectPoolConfig.Builder {

        @Override
        Builder setMinSize(int minSize);

        @Override
        Builder setMaxSize(int maxSize);

        @Override
        Builder setObjectFactory(Supplier<?> objectFactory);

        @Override
        AutoScaleObjectPoolConfig build();

        Builder setObjectTtl(long objectTtl);

        Builder setMaxIdleTime(long maxIdleTime);

        Builder setStaleChecker(StaleChecker staleChecker);

        Builder setStaleCheckInterval(long staleCheckInterval);

        Builder setScaleFactor(int scaleFactor);

    }

}