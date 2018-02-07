package org.mydotey.samples.designpattern.objectpool.autoscale;

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

    interface Builder<T> extends AbstractBuilder<T, Builder<T>> {

    }

    interface AbstractBuilder<T, B extends AbstractBuilder<T, B>> extends ObjectPoolConfig.AbstractBuilder<T, B> {

        B setObjectTtl(long objectTtl);

        B setMaxIdleTime(long maxIdleTime);

        B setStaleChecker(StaleChecker<T> staleChecker);

        B setCheckInterval(long checkInterval);

        B setScaleFactor(int scaleFactor);

    }

}