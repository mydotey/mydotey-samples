package org.mydotey.samples.designpattern.adapter;

public class Adapter implements ProductB {

    private ProductA _productA;

    public Adapter(ProductA productA) {
        _productA = productA;
    }

    @Override
    public void doSomeThing(Object param, Object param2) {
        _productA.doSomeThing(param, param2, new Object());
    }

}
