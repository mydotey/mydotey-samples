package org.mydotey.samples.designpattern.state;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class StateTest {

    @Test
    public void stateTest() {
        Product product = new ConcreteProduct();
        System.out
                .println("original price: " + product.getOriginalPrice() + ", final price: " + product.getFinalPrice());
        product.setState(new PromotionState());
        System.out
                .println("original price: " + product.getOriginalPrice() + ", final price: " + product.getFinalPrice());
    }

}
