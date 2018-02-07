package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPool.Entry;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPoolConfig<T> {

    int getMinSize();

    int getMaxSize();

    Supplier<T> getObjectFactory();

    Consumer<Entry<T>> getOnCreate();

    Consumer<Entry<T>> getOnClose();

    interface Builder<T> {

        Builder<T> setMinSize(int minSize);

        Builder<T> setMaxSize(int maxSize);

        Builder<T> setObjectFactory(Supplier<T> objectFactory);

        Builder<T> setOnCreate(Consumer<Entry<T>> onCreate);

        Builder<T> setOnClose(Consumer<Entry<T>> onClose);

        ObjectPoolConfig<T> build();

    }

}