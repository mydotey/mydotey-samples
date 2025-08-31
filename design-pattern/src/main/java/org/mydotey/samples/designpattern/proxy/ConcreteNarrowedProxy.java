package org.mydotey.samples.designpattern.proxy;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ConcreteNarrowedProxy implements NarrowedProxy {

    private Product _product;

    public ConcreteNarrowedProxy(Product product) {
        _product = product;
    }

    @Override
    public void doSomeThingA() {
        _product.doSomeThingA();
    }

}
