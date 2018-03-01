package org.mydotey.samples.designpattern.strategy;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class StrategyTest {

    @Test
    public void strategyTest() {
        Product product = new ConcreteProduct(10000, 0.2, 500);

        product.setStategy(new ConcreteStrategy());
        System.out.println("Product price: " + product.getPrice());

        product.setStategy(new ConcreteStrategy2());
        System.out.println("Product price: " + product.getPrice());
    }

}
