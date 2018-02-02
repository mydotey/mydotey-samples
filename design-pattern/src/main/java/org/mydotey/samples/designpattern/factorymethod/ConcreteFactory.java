package org.mydotey.samples.designpattern.factorymethod;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ConcreteFactory extends Factory {

    @Override
    protected Product newProduct() {
        return new ConcreteProduct();
    }

}
