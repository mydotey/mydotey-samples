package org.mydotey.samples.algorithm.loadbalance;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class ConcreteServer implements Server {

    @Override
    public String toString() {
        String[] parts = super.toString().split("\\.");
        return parts[parts.length - 1];
    }

}
