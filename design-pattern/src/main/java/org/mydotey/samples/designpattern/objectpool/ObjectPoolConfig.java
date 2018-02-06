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

    Consumer<Entry<T>> getOnEntryCreate();

    interface Builder<T> {

        Builder<T> setMinSize(int minSize);

        Builder<T> setMaxSize(int maxSize);

        Builder<T> setObjectFactory(Supplier<T> objectFactory);

        Builder<T> setOnEntryCreate(Consumer<Entry<T>> onEntryCreate);

        ObjectPoolConfig<T> build();

    }

}