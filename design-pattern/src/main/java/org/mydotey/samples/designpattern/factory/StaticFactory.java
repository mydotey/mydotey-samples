package org.mydotey.samples.designpattern.factory;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class StaticFactory {

    private StaticFactory() {

    }

    public static Product newProduct(String identity) {
        if (ConcreteProduct.class.getName().equals(identity))
            return new ConcreteProduct();

        if (ConcreteProduct2.class.getName().equals(identity))
            return new ConcreteProduct2();

        throw new IllegalArgumentException(identity + " is not supported.");
    }

}
