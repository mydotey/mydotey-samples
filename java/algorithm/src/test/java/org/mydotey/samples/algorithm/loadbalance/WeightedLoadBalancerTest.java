package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public abstract class WeightedLoadBalancerTest extends LoadBalancerTest {

    @Parameters(name = "{index}: servers={0}, chooseTimes={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        List<Server> servers;
        Integer chooseTimes;

        servers = Arrays.asList(new ConcreteWeightedServer(1));
        chooseTimes = 10;
        parameterValues.add(new Object[] { servers, chooseTimes });

        servers = Arrays.asList(new ConcreteWeightedServer(1), new ConcreteWeightedServer(3),
                new ConcreteWeightedServer(5), new ConcreteWeightedServer(7), new ConcreteWeightedServer(9));
        chooseTimes = 1 * 3 * 5 * 7 * 9;
        parameterValues.add(new Object[] { servers, chooseTimes });

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
        LoadBalancer innerLoadBalancer = newInnerLoadBalancer();
        return new WeightedLoadBalancer(innerLoadBalancer);
    }

    protected abstract LoadBalancer newInnerLoadBalancer();

}
