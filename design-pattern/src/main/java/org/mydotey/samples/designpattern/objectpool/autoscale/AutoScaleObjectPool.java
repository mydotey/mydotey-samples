package org.mydotey.samples.designpattern.objectpool.autoscale;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleObjectPool<T> extends ObjectPool<T> {

    @Override
    AutoScaleObjectPoolConfig<T> getConfig();

}