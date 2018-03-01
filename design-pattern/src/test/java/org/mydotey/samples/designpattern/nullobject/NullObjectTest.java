package org.mydotey.samples.designpattern.nullobject;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class NullObjectTest {

    @Test
    public void nullObjectTest() {
        Product product = new NullProduct();
        product.showMe();
    }

}
