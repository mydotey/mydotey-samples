package org.mydotey.samples.designpattern.factorymethod;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class FactoryMethodTest {

    @Test
    public void factoryMethodTest() {
        Factory factory = new ConcreteFactory();
        factory.someOperation();
    }

}
