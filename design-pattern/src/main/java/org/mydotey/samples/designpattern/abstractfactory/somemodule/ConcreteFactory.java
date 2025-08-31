package org.mydotey.samples.designpattern.abstractfactory.somemodule;

import org.mydotey.samples.designpattern.abstractfactory.Factory;
import org.mydotey.samples.designpattern.abstractfactory.Product;
import org.mydotey.samples.designpattern.abstractfactory.Product2;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ConcreteFactory implements Factory {

    @Override
    public Product newProduct() {
        return new ConcreteProduct();
    }

    @Override
    public Product2 newProduct2() {
        return new ConcreteProduct2();
    }

}
