package org.mydotey.samples.designpattern.prototype;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
@SuppressWarnings("unused")
public class Product {

    private CoreProperty _coreProperty;
    private NonCoreProperty _nonCoreProperty;

    public Product() {
        this(createCoreProperty());
    }

    private Product(CoreProperty coreProperty) {
        _coreProperty = coreProperty;
        _nonCoreProperty = createNonCoreProperty();
    }

    public Product clone() {
        return new Product(_coreProperty);
    }

    private static CoreProperty createCoreProperty() {
        // hard to create
        return new CoreProperty();
    }

    private static NonCoreProperty createNonCoreProperty() {
        // easy to create
        return new NonCoreProperty();
    }
}
