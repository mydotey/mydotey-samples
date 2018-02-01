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

    public Product newProduct() {
        return new Product();
    }

}
