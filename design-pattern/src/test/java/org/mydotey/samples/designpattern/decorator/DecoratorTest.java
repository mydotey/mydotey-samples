package org.mydotey.samples.designpattern.decorator;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class DecoratorTest {

    @Test
    public void decoratorTest() {
        Product product = new ConcreteProduct();
        Product decorator = new Decorator(product);
        decorator.doSomeThing();

        Product product2 = new ConcreteProduct2();
        Product decorator2 = new Decorator(product2);
        decorator2.doSomeThing();
    }

}
