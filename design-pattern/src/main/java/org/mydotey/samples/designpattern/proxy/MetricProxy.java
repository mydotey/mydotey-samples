package org.mydotey.samples.designpattern.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class MetricProxy implements Product {

    private static Logger _logger = LoggerFactory.getLogger(MetricProxy.class);

    private Product _product;

    public MetricProxy(Product product) {
        _product = product;
    }

    @Override
    public void doSomeThingA() {
        try {
            _logger.info("doSomeThingA started.");
            _product.doSomeThingA();
        } finally {
            _logger.info("doSomeThingA done.");
        }
    }

    @Override
    public void doSomeThingB() {
        try {
            _logger.info("doSomeThingB startd.");
            _product.doSomeThingB();
        } finally {
            _logger.info("doSomeThingB done.");
        }
    }

}
