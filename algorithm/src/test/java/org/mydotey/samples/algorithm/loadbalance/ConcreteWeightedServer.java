package org.mydotey.samples.algorithm.loadbalance;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class ConcreteWeightedServer extends ConcreteServer implements WeightedServer {

    private int _weight;

    public ConcreteWeightedServer(int weight) {
        if (weight < 0)
            throw new IllegalArgumentException("weight < 0");

        _weight = weight;
    }

    @Override
    public int getWeight() {
        return _weight;
    }

}
