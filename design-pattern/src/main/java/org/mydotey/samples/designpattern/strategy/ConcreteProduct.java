package org.mydotey.samples.designpattern.strategy;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteProduct implements Product {

    private double _originalPrice;
    private double _discount;
    private double _off;

    private Strategy _strategy;

    public ConcreteProduct(double originalPrice, double discount, double off) {
        _originalPrice = originalPrice;
        _discount = discount;
        _off = off;
    }

    @Override
    public void setStategy(Strategy strategy) {
        _strategy = strategy;
    }

    @Override
    public double getPrice() {
        return _strategy.computePrice(_originalPrice, _discount, _off);
    }

}
