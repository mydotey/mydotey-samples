package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public class ConsistentHashLoadBalancerTest {

    @Parameters(name = "{index}: servers={0}, chooseTimes={1}, key={2}, chooseTimes={3}, expectedChossenServer={4}, addedServer={5}, expectedChosenServerAfterAdd={6}, removedServer={7}, expectedChosenServerAfterRemove={8}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        LoadBalancer loadBalancer;
        List<Server> servers;
        Integer key;
        Integer chooseTimes = 10;
        Server expectedChosenServer;
        Server addedServer = null;
        Server expectedChosenServerAfterAdd = null;
        Server removedServer = null;
        Server expectedChosenServerAfterRemove = null;

        loadBalancer = new ConsistentHashLoadBalancer();
        expectedChosenServer = new ConcreteHashServer(10);
        servers = Arrays.asList(new ConcreteHashServer(1), expectedChosenServer, new ConcreteHashServer(100));
        key = 5;
        parameterValues.add(new Object[] { loadBalancer, servers, key, chooseTimes, expectedChosenServer, addedServer,
                expectedChosenServerAfterAdd, removedServer, expectedChosenServerAfterRemove });

        expectedChosenServer = new ConcreteHashServer(1);
        servers = Arrays.asList(expectedChosenServer, new ConcreteHashServer(10), new ConcreteHashServer(100));
        key = 105;
        parameterValues.add(new Object[] { loadBalancer, servers, key, chooseTimes, expectedChosenServer, addedServer,
                expectedChosenServerAfterAdd, removedServer, expectedChosenServerAfterRemove });

        loadBalancer = new ConsistentHashLoadBalancer(10);
        expectedChosenServer = null;
        parameterValues.add(new Object[] { loadBalancer, servers, key, chooseTimes, expectedChosenServer, addedServer,
                expectedChosenServerAfterAdd, removedServer, expectedChosenServerAfterRemove });

        loadBalancer = new ConsistentHashLoadBalancer();
        expectedChosenServer = new ConcreteHashServer(1);
        servers = Arrays.asList(expectedChosenServer, new ConcreteHashServer(10), new ConcreteHashServer(100));
        key = 105;
        addedServer = new ConcreteHashServer(1000);
        expectedChosenServerAfterAdd = addedServer;
        parameterValues.add(new Object[] { loadBalancer, servers, key, chooseTimes, expectedChosenServer, addedServer,
                expectedChosenServerAfterAdd, removedServer, expectedChosenServerAfterRemove });
        addedServer = null;
        expectedChosenServerAfterAdd = null;

        loadBalancer = new ConsistentHashLoadBalancer();
        removedServer = new ConcreteHashServer(1000);
        expectedChosenServer = removedServer;
        expectedChosenServerAfterRemove = new ConcreteHashServer(1);
        servers = Arrays.asList(expectedChosenServerAfterRemove, new ConcreteHashServer(10),
                new ConcreteHashServer(100), removedServer);
        key = 105;
        parameterValues.add(new Object[] { loadBalancer, servers, key, chooseTimes, expectedChosenServer, addedServer,
                expectedChosenServerAfterAdd, removedServer, expectedChosenServerAfterRemove });
        removedServer = null;
        expectedChosenServerAfterRemove = null;

        return parameterValues;
    }

    @Parameter(0)
    public ConsistentHashLoadBalancer loadBalancer;

    @Parameter(1)
    public List<Server> servers;

    @Parameter(2)
    public Integer key;

    @Parameter(3)
    public int chooseTimes;

    @Parameter(4)
    public Server expectedChosenServer;

    @Parameter(5)
    public Server addedServer;

    @Parameter(6)
    public Server expectedChosenServerAfterAdd;

    @Parameter(7)
    public Server removedServer;

    @Parameter(8)
    public Server expectedChosenServerAfterRemove;

    @Before
    public void setUp() {
        loadBalancer.setServers(servers);
        loadBalancer.setKey(key);
    }

    @After
    public void tearDown() {
        loadBalancer.clearKey();
    }

    @Test
    public void chooseServer() {
        System.out.printf("\nfor key: %s\n\n", key);

        Server server = loadBalancer.chooseServer();
        Assert.assertNotNull(server);
        if (expectedChosenServer != null)
            Assert.assertEquals(expectedChosenServer, server);

        for (int i = 0; i < chooseTimes; i++) {
            Server server2 = loadBalancer.chooseServer();
            Assert.assertEquals(server, server2);
        }

        System.out.println();
    }

    @Test
    public void addServer() {
        if (addedServer == null)
            return;

        Server server = loadBalancer.chooseServer();
        Assert.assertNotNull(server);
        Assert.assertEquals(expectedChosenServer, server);

        List<Server> newServers = new ArrayList<>(servers);
        newServers.add(addedServer);
        loadBalancer.setServers(newServers);
        for (int i = 0; i < chooseTimes; i++) {
            Server server2 = loadBalancer.chooseServer();
            Assert.assertEquals(expectedChosenServerAfterAdd, server2);
        }

        loadBalancer.setServers(servers);
        for (int i = 0; i < chooseTimes; i++) {
            Server server2 = loadBalancer.chooseServer();
            Assert.assertEquals(expectedChosenServer, server2);
        }

        System.out.println();
    }

    @Test
    public void removeServer() {
        if (removedServer == null)
            return;

        Server server = loadBalancer.chooseServer();
        Assert.assertEquals(expectedChosenServer, server);

        List<Server> newServers = new ArrayList<>(servers);
        newServers.remove(removedServer);
        loadBalancer.setServers(newServers);
        for (int i = 0; i < chooseTimes; i++) {
            Server server2 = loadBalancer.chooseServer();
            Assert.assertEquals(expectedChosenServerAfterRemove, server2);
        }

        loadBalancer.setServers(servers);
        for (int i = 0; i < chooseTimes; i++) {
            Server server2 = loadBalancer.chooseServer();
            Assert.assertEquals(expectedChosenServer, server2);
        }

        System.out.println();
    }

}
