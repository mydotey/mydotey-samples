package org.mydotey.samples.designpattern.strategy;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteStrategy implements Strategy {

    @Override
    public double computePrice(double originalPrice, double discount, double off) {
        return originalPrice;
    }

}
