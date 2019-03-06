package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;
import java.util.function.Function;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {

    private Function<Integer, Integer> _random;

    public RandomLoadBalancer(Function<Integer, Integer> random) {
        ObjectExtension.requireNonNull(random, "random");

        _random = random;
    }

    @Override
    public Server chooseServer() {
        List<Server> servers = getServers();
        int index = _random.apply(servers.size());
        return servers.get(index);
    }

}
