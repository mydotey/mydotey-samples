package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 6, 2018
 */
public interface ObjectPoolConfig {

    int getMinSize();

    int getMaxSize();

    Supplier<?> getObjectFactory();

    interface Builder {

        Builder setMinSize(int minSize);

        Builder setMaxSize(int maxSize);

        Builder setObjectFactory(Supplier<?> objectFactory);

        ObjectPoolConfig build();

    }

}