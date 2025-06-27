package org.mydotey.samples.designpattern.memento2;

import java.util.Stack;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class Product implements Mementoable {

    private double _price;

    private Stack<ProductMemento> _mementos = new Stack<>();

    public Product(double price) {
        _price = price;
    }

    public double getPrice() {
        return _price;
    }

    public void setPrice(double price) {
        _price = price;
    }

    @Override
    public void createMemento() {
        _mementos.push(new ProductMemento(_price));
    }

    @Override
    public void rollback() {
        ProductMemento memento = _mementos.pop();
        if (memento != null)
            _price = memento.getPrice();
    }

}
