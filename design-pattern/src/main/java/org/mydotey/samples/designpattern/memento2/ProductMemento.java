package org.mydotey.samples.designpattern.memento2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ProductMemento implements Memento {

    private double _price;

    public ProductMemento(double price) {
        _price = price;
    }

    public double getPrice() {
        return _price;
    }

}
