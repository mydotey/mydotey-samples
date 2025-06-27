package org.mydotey.samples.designpattern.templatemethod;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public abstract class Product {

    public void showMe() {
        System.out.println("I'm a product.");
        System.out.println("My price is 10000.");
        System.out.println("My production steps are: ");
        showProductSteps();
        System.out.println("That's all.");
    }

    protected abstract void showProductSteps();

}
