package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleObjectPoolConfig<T> extends ObjectPoolConfig<T> {

    long getObjectTtl();

    long getMaxIdleTime();

    StaleChecker<T> getStaleChecker();

    long getStaleCheckInterval();

    int getScaleFactor();

    interface Builder<T> extends ObjectPoolConfig.Builder<T> {

        @Override
        Builder<T> setMinSize(int minSize);

        @Override
        Builder<T> setMaxSize(int maxSize);

        @Override
        Builder<T> setObjectFactory(Supplier<T> objectFactory);

        @Override
        AutoScaleObjectPoolConfig<T> build();

        Builder<T> setObjectTtl(long objectTtl);

        Builder<T> setMaxIdleTime(long maxIdleTime);

        Builder<T> setStaleChecker(StaleChecker<T> staleChecker);

        Builder<T> setStaleCheckInterval(long staleCheckInterval);

        Builder<T> setScaleFactor(int scaleFactor);

    }

}