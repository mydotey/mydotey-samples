package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPoolConfig<T> {

    int getMinSize();

    int getMaxSize();

    Supplier<T> getObjectFactory();

    interface Builder<T> {

        Builder<T> setMinSize(int minSize);

        Builder<T> setMaxSize(int maxSize);

        Builder<T> setObjectFactory(Supplier<T> objectFactory);

        ObjectPoolConfig<T> build();

    }

}