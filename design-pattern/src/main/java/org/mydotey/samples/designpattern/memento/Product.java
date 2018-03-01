package org.mydotey.samples.designpattern.memento;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class Product implements Mementoable {

    private double _price;

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
    public ProductMemento createMemento() {
        return new ProductMemento(_price);
    }

    @Override
    public void restore(Memento memento) {
        _price = ((ProductMemento) memento).getPrice();
    }

}
