package org.mydotey.samples.designpattern.flyweight;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class FlyweightTest {

    @Test
    public void flyweightTest() {
        Product product = new ConcreteProduct(10);
        product.showMe(20);
    }

}
