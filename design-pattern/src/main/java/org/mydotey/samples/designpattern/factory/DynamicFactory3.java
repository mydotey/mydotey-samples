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

    private static ConcurrentHashMap<Class<?>, Supplier<?>> _clazzCache = new ConcurrentHashMap<>();

    private DynamicFactory3() {

    }

    public static void register(Class<?> clazz, Supplier<?> supplier) {
        _clazzCache.put(clazz, supplier);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newProduct(Class<T> clazz) {
        Supplier<T> supplier = (Supplier<T>) _clazzCache.get(clazz);
        Checkers.requireNonNull(supplier, "clazz %s has no supplier.", clazz);
        return supplier.get();
    }

}
