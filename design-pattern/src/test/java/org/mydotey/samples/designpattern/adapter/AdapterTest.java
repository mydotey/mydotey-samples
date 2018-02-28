package org.mydotey.samples.designpattern.adapter;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class AdapterTest {

    @Test
    public void adapterTest() {
        ProductA productA = new ProductA() {
            @Override
            public void doSomeThing(Object param, Object param2, Object param3) {
                System.out.println("I'm ProductA");
            }
        };

        ProductB productB = new Adapter(productA);
        productB.doSomeThing(new Object(), new Object());
    }

}
