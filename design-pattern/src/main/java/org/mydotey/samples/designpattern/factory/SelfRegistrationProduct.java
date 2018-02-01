package org.mydotey.samples.designpattern.factory;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class SelfRegistrationProduct implements Product {

    static {
        DynamicFactory.register(SelfRegistrationProduct.class.getName(), new SelfRegistrationProduct());
    }

}
