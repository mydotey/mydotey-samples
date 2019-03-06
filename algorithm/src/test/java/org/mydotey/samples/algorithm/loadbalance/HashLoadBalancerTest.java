package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class HashLoadBalancerTest extends LoadBalanceTest {

    @Parameters(name = "{index}: servers={0}, chooseTimes={1}, keys={2}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        List<Server> servers;
        Integer chooseTimes = 10;
        List<String> keys;

        servers = Arrays.asList(new ConcreteServer());

        keys = Arrays.asList("a");
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        keys = Arrays.asList(ObjectExtension.<String> NULL());
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        keys = Arrays.asList("a", "b", "c");
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        servers = Arrays.asList(new ConcreteServer(), new ConcreteServer(), new ConcreteServer(), new ConcreteServer(),
                new ConcreteServer());

        keys = Arrays.asList("a");
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        keys = Arrays.asList(ObjectExtension.<String> NULL());
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        keys = Arrays.asList("a", "b", "c");
        parameterValues.add(new Object[] { servers, chooseTimes, keys });

        return parameterValues;
    }

    @Parameter(2)
    public List<String> keys;

    @Override
    protected LoadBalancer newLoadBalancer() {
        return new HashLoadBalancer();
    }

    @Override
    protected HashLoadBalancer getLoadBalancer() {
        return (HashLoadBalancer) super.getLoadBalancer();
    }

    @Test
    public void chooseServer() {
        HashLoadBalancer loadBalancer = getLoadBalancer();
        for (String key : keys) {
            System.out.printf("\nfor key: %s\n\n", key);
            loadBalancer.setKey(key);
            Server server = loadBalancer.chooseServer();
            System.out.printf("server: %s\n", server);
            for (int i = 0; i < chooseTimes; i++) {
                Server currentServer = loadBalancer.chooseServer();
                Assert.assertNotNull(currentServer);
                Assert.assertTrue(server == currentServer);
                System.out.printf("times: %s, current-server: %s\n", i, currentServer);
            }
            loadBalancer.clearKey();
            System.out.println();
        }

        System.out.println();
    }

}
