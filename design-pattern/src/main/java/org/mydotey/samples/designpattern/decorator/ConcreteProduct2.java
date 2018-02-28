package org.mydotey.samples.designpattern.decorator;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ConcreteProduct2 implements Product {

    @Override
    public void doSomeThing() {
        throw new RuntimeException("I'm bad and throw exception.");
    }

}
