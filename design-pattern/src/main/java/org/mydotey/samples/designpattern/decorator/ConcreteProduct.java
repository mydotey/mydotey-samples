package org.mydotey.samples.designpattern.decorator;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ConcreteProduct implements Product {

    @Override
    public void doSomeThing() {
        System.out.println("I'm nice and never throw exception.");
    }

}
