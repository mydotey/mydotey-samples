package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.io.Closeable;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleObjectPool<T> extends ObjectPool<T>, Closeable {

    @Override
    AutoScaleObjectPoolConfig<T> getConfig();

}