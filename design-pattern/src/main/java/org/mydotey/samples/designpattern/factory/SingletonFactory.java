package org.mydotey.samples.designpattern.factory;

import org.mydotey.samples.designpattern.SingletonSupplier;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class SingletonFactory {

    private static SingletonSupplier<SingletonFactory> _supplier = new SingletonSupplier<>(
            () -> new SingletonFactory());

    public static SingletonFactory getInstance() {
        return _supplier.get();
    }

    private SingletonFactory() {

    }

    public Product newProduct(String identity) {
        if (ProductImpl.class.getName().equals(identity))
            return new ProductImpl();

        if (ProductImpl2.class.getName().equals(identity))
            return new ProductImpl2();

        throw new IllegalArgumentException(identity + " is not supported.");
    }

}
