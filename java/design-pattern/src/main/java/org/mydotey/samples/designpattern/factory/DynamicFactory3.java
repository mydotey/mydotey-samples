package org.mydotey.samples.designpattern.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.Checkers;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class DynamicFactory3 {

    private static ConcurrentHashMap<Class<?>, Supplier<Product>> _clazzCache = new ConcurrentHashMap<>();

    private DynamicFactory3() {

    }

    public static void register(Class<?> clazz, Supplier<Product> supplier) {
        _clazzCache.put(clazz, supplier);
    }

    public static Product newProduct(Class<?> clazz) {
        Supplier<Product> supplier = _clazzCache.get(clazz);
        Checkers.requireNonNull(supplier, "clazz %s has no supplier.", clazz);
        return supplier.get();
    }

}
