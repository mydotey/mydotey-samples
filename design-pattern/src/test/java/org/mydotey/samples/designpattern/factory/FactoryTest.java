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
    public void factoryTest() {
        Factory factory = new Factory();
        Product product = factory.newProduct();
        Assert.assertNotNull(product);
    }

    @Test
    public void singletonFactoryTest() {
        Product product = SingletonFactory.getInstance().newProduct();
        Assert.assertNotNull(product);
    }

    @Test
    public void staticFactoryTest() {
        Product product = StaticFactory.newProduct();
        Assert.assertNotNull(product);
    }

    @Test
    public void dynamicFactoryTest() {
        String identity = "type1";
        String identity2 = "type2";
        Product product = new Product();
        Object obj = new Object();

        // on init
        DynamicFactory.register(identity, product);
        DynamicFactory.register(identity2, obj);

        // after init
        Object product2 = DynamicFactory.newProduct(identity);
        Assert.assertEquals(product, product2);
        Object obj2 = DynamicFactory.newProduct(identity2);
        Assert.assertEquals(obj, obj2);
    }

    @Test
    public void dynamicFactory2Test() {
        String identity = "type1";
        String identity2 = "type2";

        // on init
        DynamicFactory2.register(identity, Product.class);
        DynamicFactory2.register(identity2, Object.class);

        // after init
        Object product = DynamicFactory2.newProduct(identity);
        Assert.assertTrue(product.getClass() == Product.class);
        Object obj = DynamicFactory2.newProduct(identity2);
        Assert.assertTrue(obj.getClass() == Object.class);
    }

    @Test
    public void dynamicFactory3Test() {
        // on init
        DynamicFactory3.register(Product.class, () -> new Product());
        DynamicFactory3.register(Object.class, () -> new Object());

        // after init
        Object product = DynamicFactory3.newProduct(Product.class);
        Assert.assertTrue(product.getClass() == Product.class);
        Object obj = DynamicFactory3.newProduct(Object.class);
        Assert.assertTrue(obj.getClass() == Object.class);
    }

}
