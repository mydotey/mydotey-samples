package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public interface LoadBalancer {

    List<Server> getServers();

    void setServers(List<Server> servers);

    Server chooseServer();

}
