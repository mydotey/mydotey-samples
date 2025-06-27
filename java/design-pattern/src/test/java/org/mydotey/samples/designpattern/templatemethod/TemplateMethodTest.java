package org.mydotey.samples.designpattern.templatemethod;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class TemplateMethodTest {

    @Test
    public void templateMethodTest() {
        Product product = new ConcreteProduct();
        product.showMe();
    }

}
