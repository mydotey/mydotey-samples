package org.mydotey.samples.algorithm.loadbalance;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class RandomWeightedLoadBalancerTest extends WeightedLoadBalancerTest {

    @Override
    protected LoadBalancer newInnerLoadBalancer() {
        return new RandomLoadBalancer(size -> ThreadLocalRandom.current().nextInt(size));
    }

}
