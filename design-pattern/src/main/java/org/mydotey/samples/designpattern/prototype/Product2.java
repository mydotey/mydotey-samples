package org.mydotey.samples.designpattern.prototype;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
@SuppressWarnings("unused")
public class Product2 {

    public static final Product2 DEFAULT_INSTANCE = new Product2();

    private CoreProperty _coreProperty;
    private NonCoreProperty _nonCoreProperty;

    private Product2() {
        this(createCoreProperty());
    }

    private Product2(CoreProperty coreProperty) {
        _coreProperty = coreProperty;
    }

    public void setNonCoreProperty(NonCoreProperty nonCoreProperty) {
        _nonCoreProperty = nonCoreProperty;
    }

    public Product2 clone() {
        return new Product2(_coreProperty);
    }

    private static CoreProperty createCoreProperty() {
        // hard to create
        return new CoreProperty();
    }

}
