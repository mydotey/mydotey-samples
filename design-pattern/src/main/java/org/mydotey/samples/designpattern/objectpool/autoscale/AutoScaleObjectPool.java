package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.io.Closeable;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface AutoScaleObjectPool extends ObjectPool, Closeable {

    @Override
    AutoScaleObjectPoolConfig getConfig();

}