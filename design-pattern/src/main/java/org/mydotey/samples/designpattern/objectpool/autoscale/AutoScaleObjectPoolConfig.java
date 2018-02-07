package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;
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

    long getCheckInterval();

    int getScaleFactor();

    interface Builder<T> extends ObjectPoolConfig.Builder<T> {

        @Override
        Builder<T> setMinSize(int minSize);

        @Override
        Builder<T> setMaxSize(int maxSize);

        @Override
        Builder<T> setObjectFactory(Supplier<T> objectFactory);

        @Override
        Builder<T> setOnEntryCreate(Consumer<ObjectPool.Entry<T>> onEntryCreate);

        @Override
        Builder<T> setOnClose(Consumer<T> onClose);

        @Override
        AutoScaleObjectPoolConfig<T> build();

        Builder<T> setObjectTtl(long objectTtl);

        Builder<T> setMaxIdleTime(long maxIdleTime);

        Builder<T> setStaleChecker(StaleChecker<T> staleChecker);

        Builder<T> setCheckInterval(long checkInterval);

        Builder<T> setScaleFactor(int scaleFactor);

    }

}