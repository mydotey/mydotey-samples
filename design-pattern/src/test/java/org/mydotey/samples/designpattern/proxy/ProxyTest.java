package org.mydotey.samples.designpattern.proxy;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class ProxyTest {

    private Product newProduct() {
        return new Product() {

            @Override
            public void doSomeThingB() {
                System.out.println("real product doing B");
            }

            @Override
            public void doSomeThingA() {
                System.out.println("real product doing A");
            }
        };
    }

    @Test
    public void narrowedProxyTest() {
        Product product = newProduct();
        NarrowedProxy proxy = new ConcreteNarrowedProxy(product);
        proxy.doSomeThingA();
    }

    @Test
    public void virtualProxyTest() {
        Product proxy = new VirtualProxy(() -> newProduct());
        proxy.doSomeThingA();
        proxy.doSomeThingB();
    }

    @Test
    public void metricProxyTest() {
        Product product = newProduct();
        Product proxy = new MetricProxy(product);
        proxy.doSomeThingA();
        proxy.doSomeThingB();
    }

    @Test
    public void dynamicMetricProxyTest() {
        Product product = newProduct();
        Product proxy = DynamicMetricProxy.getProxy(product);
        proxy.doSomeThingA();
        proxy.doSomeThingB();
    }

}
