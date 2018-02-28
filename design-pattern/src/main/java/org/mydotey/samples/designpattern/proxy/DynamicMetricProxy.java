package org.mydotey.samples.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class DynamicMetricProxy {

    private static Logger _logger = LoggerFactory.getLogger(MetricProxy.class);

    public static Product getProxy(Product product) {
        return (Product) Proxy.newProxyInstance(Product.class.getClassLoader(), new Class<?>[] { Product.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        try {
                            _logger.info(method.getName() + " started.");
                            return method.invoke(product, args);
                        } finally {
                            _logger.info(method.getName() + " done.");
                        }
                    }
                });
    }

}
