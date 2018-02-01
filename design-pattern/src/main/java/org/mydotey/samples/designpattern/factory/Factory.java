package org.mydotey.samples.designpattern.factory;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class Factory {

    public Product newProduct(String identity) {
        if (ProductImpl.class.getName().equals(identity))
            return new ProductImpl();

        if (ProductImpl2.class.getName().equals(identity))
            return new ProductImpl2();

        throw new IllegalArgumentException(identity + " is not supported.");
    }

}
