package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class WeightedLoadBalancer extends AbstractLoadBalancer {

    private LoadBalancer _loadBalancer;

    public WeightedLoadBalancer(LoadBalancer loadBalancer) {
        ObjectExtension.requireNonNull(loadBalancer, "loadBalancer");
        _loadBalancer = loadBalancer;
    }

    @Override
    public void setServers(List<Server> servers) {
        if (servers != null)
            servers.forEach(s -> {
                if (s instanceof WeightedServer)
                    return;
                throw new IllegalArgumentException("server " + s + " is not WeightedServer");
            });
        super.setServers(servers);

        List<Server> fakeServers = new ArrayList<>();
        servers.forEach(s -> {
            WeightedServer weightedServer = (WeightedServer) s;
            for (int i = 0; i < weightedServer.getWeight(); i++)
                fakeServers.add(weightedServer);
        });
        Collections.shuffle(fakeServers);
        _loadBalancer.setServers(fakeServers);
    }

    @Override
    public Server chooseServer() {
        return _loadBalancer.chooseServer();
    }

}
