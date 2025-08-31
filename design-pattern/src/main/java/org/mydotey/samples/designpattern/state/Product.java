package org.mydotey.samples.designpattern.state;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public interface Product {

    void setState(State state);

    double getOriginalPrice();

    double getFinalPrice();

}
