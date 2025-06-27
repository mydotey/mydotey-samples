package org.mydotey.samples.designpattern.state;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ConcreteProduct implements Product {

    private double _orginalPrice;
    private State _state;

    public ConcreteProduct() {
        _orginalPrice = 10000;
        _state = new OriginalState();
    }

    @Override
    public void setState(State state) {
        _state = state;
    }

    @Override
    public double getOriginalPrice() {
        return _orginalPrice;
    }

    @Override
    public double getFinalPrice() {
        return _orginalPrice * (1 - _state.getDiscount()) - _state.getOff();
    }

}
