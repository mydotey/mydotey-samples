package org.mydotey.samples.designpattern.factory;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class FactoryTest {

    @Test
    public void basicFactoryTest() {
        BasicFactory factory = new BasicFactory();
        Product product = factory.newProduct();
        Assert.assertNotNull(product);
    }

    @Test
    public void factoryTest() {
        Factory factory = new Factory();
        Product product = factory.newProduct(ConcreteProduct.class.getName());
        Assert.assertNotNull(product);
        Product product2 = factory.newProduct(ConcreteProduct2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void singletonFactoryTest() {
        Product product = SingletonFactory.getInstance().newProduct(ConcreteProduct.class.getName());
        Assert.assertNotNull(product);
        Product product2 = SingletonFactory.getInstance().newProduct(ConcreteProduct2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void staticFactoryTest() {
        Product product = StaticFactory.newProduct(ConcreteProduct.class.getName());
        Assert.assertNotNull(product);
        Product product2 = StaticFactory.newProduct(ConcreteProduct2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void dynamicFactoryTest() {
        Product product = new ConcreteProduct();
        Product product2 = new ConcreteProduct2();

        // on init
        DynamicFactory.register(ConcreteProduct.class.getName(), product);
        DynamicFactory.register(ConcreteProduct2.class.getName(), product2);

        // after init
        Object created = DynamicFactory.newProduct(ConcreteProduct.class.getName());
        Assert.assertEquals(product, created);
        Object created2 = DynamicFactory.newProduct(ConcreteProduct2.class.getName());
        Assert.assertEquals(product2, created2);
    }

    @Test
    public void dynamicFactory2Test() {
        // on init
        DynamicFactory2.register(ConcreteProduct.class.getName(), ConcreteProduct.class);
        DynamicFactory2.register(ConcreteProduct2.class.getName(), ConcreteProduct2.class);

        // after init
        Object product = DynamicFactory2.newProduct(ConcreteProduct.class.getName());
        Assert.assertTrue(product.getClass() == ConcreteProduct.class);
        Object product2 = DynamicFactory2.newProduct(ConcreteProduct2.class.getName());
        Assert.assertTrue(product2.getClass() == ConcreteProduct2.class);
    }

    @Test
    public void dynamicFactory3Test() {
        // on init
        DynamicFactory3.register(ConcreteProduct.class, () -> new ConcreteProduct());
        DynamicFactory3.register(ConcreteProduct2.class, () -> new ConcreteProduct2());

        // after init
        Product product = DynamicFactory3.newProduct(ConcreteProduct.class);
        Assert.assertTrue(product.getClass() == ConcreteProduct.class);
        Product product2 = DynamicFactory3.newProduct(ConcreteProduct2.class);
        Assert.assertTrue(product2.getClass() == ConcreteProduct2.class);
    }

    @Test
    public void dynamicFactory3TestWithSelfRegistration() {
        String className = "org.mydotey.samples.designpattern.factory.SelfRegistrationProduct";

        // on init
        try {
            Class.forName(className);
        } catch (Exception e) {
            Assert.fail("should not have exception here");
        }

        // after init
        Product product = DynamicFactory.newProduct(className);
        Assert.assertEquals(className, product.getClass().getName());
    }

}
