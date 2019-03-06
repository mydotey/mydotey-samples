package org.mydotey.samples.algorithm.loadbalance;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class RoundRobinWeightedLoadBalancerTest extends WeightedLoadBalancerTest {

    @Override
    protected LoadBalancer newInnerLoadBalancer() {
        return new RoundRobinLoadBalancer();
    }

}
