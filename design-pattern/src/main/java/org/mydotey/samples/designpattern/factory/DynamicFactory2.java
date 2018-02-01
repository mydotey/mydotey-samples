package org.mydotey.samples.designpattern.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.mydotey.samples.designpattern.Checkers;
import org.mydotey.samples.designpattern.EmptyValues;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class DynamicFactory2 {

    private static ConcurrentHashMap<Object, Class<?>> _clazzCache = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<Class<?>, Constructor<?>> _constructorCache = new ConcurrentHashMap<>();

    private static Function<? super Class<?>, Constructor<?>> _constructorGenerator = c -> {
        try {
            return c.getDeclaredConstructor(new Class<?>[0]);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException(c + " has no default constructor.", e);
        }
    };

    private DynamicFactory2() {

    }

    public static void register(Object identity, Class<?> clazz) {
        _clazzCache.put(identity, clazz);
    }

    public static Object newProduct(Object identity) {
        Class<?> clazz = _clazzCache.get(identity);
        Checkers.requireNonNull(clazz, "%s is not registered.", identity);
        Constructor<?> constructor = _constructorCache.computeIfAbsent(clazz, _constructorGenerator);
        try {
            return constructor.newInstance(EmptyValues.EMPTY_OBJECT_ARRAY);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new UnsupportedOperationException("default constructor of " + clazz + " cannot be invoked.", e);
        }
    }

}
