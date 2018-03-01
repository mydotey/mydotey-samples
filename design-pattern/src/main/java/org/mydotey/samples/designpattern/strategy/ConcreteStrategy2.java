package org.mydotey.samples.designpattern.strategy;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteStrategy2 implements Strategy {

    @Override
    public double computePrice(double originalPrice, double discount, double off) {
        return originalPrice * (1 - discount) - off;
    }

}
