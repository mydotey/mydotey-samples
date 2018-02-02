package org.mydotey.samples.designpattern.prototype;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class PrototypeTest {

    @Test
    public void prototypeTest() {
        Product product = new Product();

        Product product2 = product.clone();
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);

        Product product3 = product2.clone();
        Assert.assertNotNull(product3);
        Assert.assertNotEquals(product2, product3);
    }

    @Test
    public void prototypeTest2() {
        Product2 product = Product2.DEFAULT_INSTANCE.clone();
        Assert.assertNotNull(product);
        Assert.assertNotEquals(Product2.DEFAULT_INSTANCE, product);

        Product2 product2 = product.clone();
        Assert.assertNotNull(product2);
        Assert.assertNotEquals(product, product2);
    }

}
