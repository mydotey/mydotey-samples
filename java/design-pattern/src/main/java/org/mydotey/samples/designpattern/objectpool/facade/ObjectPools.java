package org.mydotey.samples.designpattern.objectpool.facade;

import org.mydotey.samples.designpattern.objectpool.DefaultObjectPool;
import org.mydotey.samples.designpattern.objectpool.DefaultObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.ObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPool;
import org.mydotey.samples.designpattern.objectpool.autoscale.AutoScaleObjectPoolConfig;
import org.mydotey.samples.designpattern.objectpool.autoscale.DefaultAutoScaleObjectPool;
import org.mydotey.samples.designpattern.objectpool.autoscale.DefaultAutoScaleObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 7, 2018
 */
public class ObjectPools {

    public static <T> ObjectPoolConfig.Builder<T> newObjectPoolConfigBuilder() {
        return new DefaultObjectPoolConfig.Builder<T>();
    }

    public static <T> ObjectPool<T> newObjectPool(ObjectPoolConfig<T> config) {
        return new DefaultObjectPool<>(config);
    }

    public static <T> AutoScaleObjectPoolConfig.Builder<T> newAutoScaleObjectPoolConfigBuilder() {
        return new DefaultAutoScaleObjectPoolConfig.Builder<T>();
    }

    public static <T> AutoScaleObjectPool<T> newAutoScaleObjectPool(AutoScaleObjectPoolConfig<T> config) {
        return new DefaultAutoScaleObjectPool<>(config);
    }

}
