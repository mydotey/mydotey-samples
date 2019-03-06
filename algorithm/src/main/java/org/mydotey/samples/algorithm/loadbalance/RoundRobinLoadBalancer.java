package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class RoundRobinLoadBalancer extends AbstractLoadBalancer {

    private AtomicInteger _count = new AtomicInteger();

    @Override
    public Server chooseServer() {
        List<Server> servers = getServers();
        int count = _count.getAndIncrement();
        count &= Integer.MAX_VALUE;
        int index = count % servers.size();
        return servers.get(index);
    }

}
