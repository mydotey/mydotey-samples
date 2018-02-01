package org.mydotey.samples.designpattern.factory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class DynamicFactory {

    private static ConcurrentHashMap<Object, Object> _objectCache = new ConcurrentHashMap<>();

    private DynamicFactory() {

    }

    public static void register(Object identity, Object instance) {
        _objectCache.put(identity, instance);
    }

    public static Object newProduct(Object identity) {
        return _objectCache.get(identity);
    }

}
