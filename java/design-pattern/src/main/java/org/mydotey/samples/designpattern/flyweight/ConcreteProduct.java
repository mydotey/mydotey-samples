package org.mydotey.samples.designpattern.flyweight;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ConcreteProduct implements Product {

    private Object _intrinsicState;

    public ConcreteProduct(Object intrinsicState) {
        _intrinsicState = intrinsicState;
    }

    @Override
    public Object getIntrinsicState() {
        return _intrinsicState;
    }

    @Override
    public void showMe(Object extrinsicState) {
        System.out.println("I'm intrinsic + extrinsic: " + _intrinsicState + ", " + extrinsicState);
    }

}
