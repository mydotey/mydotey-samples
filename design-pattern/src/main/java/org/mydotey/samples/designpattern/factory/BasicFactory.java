package org.mydotey.samples.designpattern.factory;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class BasicFactory {

    public Product newProduct() {
        return new ConcreteProduct();
    }

}
