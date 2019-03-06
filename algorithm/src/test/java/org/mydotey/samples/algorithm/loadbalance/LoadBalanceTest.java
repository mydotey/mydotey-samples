package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public abstract class LoadBalanceTest {

    @Parameter(0)
    public List<Server> servers;

    @Parameter(1)
    public int chooseTimes;

    private LoadBalancer _loadBalancer;

    protected abstract LoadBalancer newLoadBalancer();

    @Before
    public void setUp() {
        System.out.printf("\nservers: %s\n\n", servers);

        _loadBalancer = newLoadBalancer();
        _loadBalancer.setServers(servers);
    }

    @Test
    public void chooseServer() {
        for (int i = 0; i < chooseTimes; i++) {
            Server server = _loadBalancer.chooseServer();
            Assert.assertNotNull(server);
            System.out.printf("times: %s, server: %s\n", i, server);
        }

        System.out.println();
    }

}
