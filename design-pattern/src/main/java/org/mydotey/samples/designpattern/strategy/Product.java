package org.mydotey.samples.designpattern.strategy;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Product {

    void setStategy(Strategy strategy);

    double getPrice();

}
