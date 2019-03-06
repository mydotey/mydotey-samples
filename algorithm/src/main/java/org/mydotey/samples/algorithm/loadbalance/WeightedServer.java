package org.mydotey.samples.algorithm.loadbalance;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public interface WeightedServer extends Server {

    int getWeight();

}
