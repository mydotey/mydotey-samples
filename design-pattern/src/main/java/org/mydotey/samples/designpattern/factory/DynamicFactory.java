package org.mydotey.samples.designpattern.factory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class DynamicFactory {

    private static ConcurrentHashMap<String, Product> _objectCache = new ConcurrentHashMap<>();

    private DynamicFactory() {

    }

    public static void register(String identity, Product instance) {
        _objectCache.put(identity, instance);
    }

    public static Product newProduct(String identity) {
        return _objectCache.get(identity);
    }

}
