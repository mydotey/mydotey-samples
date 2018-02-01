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
        Product product = factory.newProduct(ProductImpl.class.getName());
        Assert.assertNotNull(product);
        Product product2 = factory.newProduct(ProductImpl2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void singletonFactoryTest() {
        Product product = SingletonFactory.getInstance().newProduct(ProductImpl.class.getName());
        Assert.assertNotNull(product);
        Product product2 = SingletonFactory.getInstance().newProduct(ProductImpl2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void staticFactoryTest() {
        Product product = StaticFactory.newProduct(ProductImpl.class.getName());
        Assert.assertNotNull(product);
        Product product2 = StaticFactory.newProduct(ProductImpl2.class.getName());
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

    @Test
    public void dynamicFactoryTest() {
        Product product = new ProductImpl();
        Product product2 = new ProductImpl2();

        // on init
        DynamicFactory.register(ProductImpl.class.getName(), product);
        DynamicFactory.register(ProductImpl2.class.getName(), product2);

        // after init
        Object created = DynamicFactory.newProduct(ProductImpl.class.getName());
        Assert.assertEquals(product, created);
        Object created2 = DynamicFactory.newProduct(ProductImpl2.class.getName());
        Assert.assertEquals(product2, created2);
    }

    @Test
    public void dynamicFactory2Test() {
        // on init
        DynamicFactory2.register(ProductImpl.class.getName(), ProductImpl.class);
        DynamicFactory2.register(ProductImpl2.class.getName(), ProductImpl2.class);

        // after init
        Object product = DynamicFactory2.newProduct(ProductImpl.class.getName());
        Assert.assertTrue(product.getClass() == ProductImpl.class);
        Object product2 = DynamicFactory2.newProduct(ProductImpl2.class.getName());
        Assert.assertTrue(product2.getClass() == ProductImpl2.class);
    }

    @Test
    public void dynamicFactory3Test() {
        // on init
        DynamicFactory3.register(ProductImpl.class, () -> new ProductImpl());
        DynamicFactory3.register(ProductImpl2.class, () -> new ProductImpl2());

        // after init
        Product product = DynamicFactory3.newProduct(ProductImpl.class);
        Assert.assertTrue(product.getClass() == ProductImpl.class);
        Product product2 = DynamicFactory3.newProduct(ProductImpl2.class);
        Assert.assertTrue(product2.getClass() == ProductImpl2.class);
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
