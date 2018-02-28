package org.mydotey.samples.designpattern.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class Decorator implements Product {

    private static Logger _logger = LoggerFactory.getLogger(Decorator.class);

    private Product _product;

    public Decorator(Product product) {
        _product = product;
    }

    @Override
    public void doSomeThing() {
        try {
            _product.doSomeThing();
        } catch (Exception e) {
            _logger.info("decorator caught exception and make sure no exception threw for the invoke", e);
        }
    }

}
