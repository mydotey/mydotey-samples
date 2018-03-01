package org.mydotey.samples.designpattern.memento;

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

        ProductMemento memento = product.createMemento();
        product.setPrice(20000);
        System.out.println("price: " + product.getPrice());

        product.restore(memento);
        System.out.println("price: " + product.getPrice());
    }

}
