package org.mydotey.samples.designpattern.memento2;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class MementoTest {

    @Test
    public void mementoTest() {
        Product product = new Product(10000);
        System.out.println("price: " + product.getPrice());

        product.createMemento();
        product.setPrice(20000);
        System.out.println("price: " + product.getPrice());

        product.rollback();
        System.out.println("price: " + product.getPrice());
    }

}
