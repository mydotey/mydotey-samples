package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class RandomLoadBalancerTest extends LoadBalanceTest {

    @Parameters(name = "{index}: servers={0}, chooseTimes={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        List<Server> servers;
        Integer chooseTimes;

        servers = Arrays.asList(new ConcreteServer());
        chooseTimes = 10;
        parameterValues.add(new Object[] { servers, chooseTimes });

        servers = Arrays.asList(new ConcreteServer(), new ConcreteServer(), new ConcreteServer(), new ConcreteServer(),
                new ConcreteServer());
        chooseTimes = 5;
        parameterValues.add(new Object[] { servers, chooseTimes });

        chooseTimes = 10;
        parameterValues.add(new Object[] { servers, chooseTimes });

        chooseTimes = 20;
        parameterValues.add(new Object[] { servers, chooseTimes });

        chooseTimes = 50;
        parameterValues.add(new Object[] { servers, chooseTimes });

        chooseTimes = 100;
        parameterValues.add(new Object[] { servers, chooseTimes });

        return parameterValues;
    }

    @Override
    protected LoadBalancer newLoadBalancer() {
        return new RandomLoadBalancer(size -> ThreadLocalRandom.current().nextInt(size));
    }

}
