package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mydotey.java.ObjectExtension;
import org.mydotey.java.collection.CollectionExtension;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public abstract class AbstractLoadBalancer implements LoadBalancer {

    private volatile List<Server> _servers;

    @Override
    public List<Server> getServers() {
        if (CollectionExtension.isEmpty(_servers))
            throw new IllegalStateException("servers is empty");

        return _servers;
    }

    @Override
    public synchronized void setServers(List<Server> servers) {
        ObjectExtension.requireNonEmpty(servers, "servers");

        _servers = Collections.unmodifiableList(new ArrayList<>(servers));
    }

}
