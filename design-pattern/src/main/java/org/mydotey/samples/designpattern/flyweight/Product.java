package org.mydotey.samples.designpattern.flyweight;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public interface Product {

    Object getIntrinsicState();

    void showMe(Object extrinsicState);

}
