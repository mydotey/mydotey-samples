package org.mydotey.samples.designpattern.abstractfactory;

import org.junit.Assert;
import org.junit.Test;
import org.mydotey.samples.designpattern.abstractfactory.somemodule.ConcreteFactory;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class AbstractFactoryTest {

    @Test
    public void abstractFactoryTest() {
        Factory factory = new ConcreteFactory();
        Product product = factory.newProduct();
        Product2 product2 = factory.newProduct2();
        Assert.assertNotNull(product);
        Assert.assertNotNull(product2);
    }
}
