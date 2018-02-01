package org.mydotey.samples.designpattern.factory;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class StaticFactory {

    private StaticFactory() {

    }

    public static Product newProduct() {
        return new Product();
    }

}
