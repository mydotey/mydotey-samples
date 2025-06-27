package org.mydotey.samples.designpattern.proxy;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class VirtualProxy implements Product {

    private Supplier<Product> _supplier;
    private WeakReference<Product> _reference;

    public VirtualProxy(Supplier<Product> supplier) {
        _supplier = supplier;
        _reference = new WeakReference<Product>(null);
    }

    @Override
    public void doSomeThingA() {
        get().doSomeThingA();
    }

    @Override
    public void doSomeThingB() {
        get().doSomeThingB();
    }

    private Product get() {
        Product product = _reference.get();
        if (product == null) {
            synchronized (this) {
                product = _reference.get();
                if (product == null) {
                    product = _supplier.get();
                    _reference = new WeakReference<Product>(product);
                }
            }
        }

        return product;
    }

}
