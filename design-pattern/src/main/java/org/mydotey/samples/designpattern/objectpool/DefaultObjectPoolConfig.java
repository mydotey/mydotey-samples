package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class DefaultObjectPoolConfig<T> implements ObjectPoolConfig<T>, Cloneable {

    public static <T> Builder<T> newBuilder() {
        return new Builder<T>();
    }

    private int minSize;
    private int maxSize;
    private Supplier<T> objectFactory;

    protected DefaultObjectPoolConfig() {

    }

    @Override
    public int getMinSize() {
        return minSize;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public Supplier<T> getObjectFactory() {
        return objectFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected DefaultObjectPoolConfig<T> clone() {
        try {
            return (DefaultObjectPoolConfig<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static class Builder<T> implements ObjectPoolConfig.Builder<T> {

        protected DefaultObjectPoolConfig<T> _config;

        protected Builder() {
            _config = newPoolConfig();
        }

        protected DefaultObjectPoolConfig<T> newPoolConfig() {
            return new DefaultObjectPoolConfig<T>();
        }

        protected DefaultObjectPoolConfig<T> getPoolConfig() {
            return _config;
        }

        @Override
        public Builder<T> setMinSize(int minSize) {
            _config.minSize = minSize;
            return this;
        }

        @Override
        public Builder<T> setMaxSize(int maxSize) {
            _config.maxSize = maxSize;
            return this;
        }

        @Override
        public Builder<T> setObjectFactory(Supplier<T> objectFactory) {
            _config.objectFactory = objectFactory;
            return this;
        }

        @Override
        public DefaultObjectPoolConfig<T> build() {
            if (_config.minSize < 0)
                throw new IllegalStateException("minSize is invalid: " + _config.minSize);

            if (_config.maxSize <= 0)
                throw new IllegalStateException("maxSize is invalid: " + _config.maxSize);

            if (_config.minSize > _config.maxSize)
                throw new IllegalStateException(
                        "minSize is larger than maxSiz. minSize: " + _config.minSize + ", maxSize: " + _config.maxSize);

            if (_config.objectFactory == null)
                throw new IllegalStateException("objectFactory is not set");

            return _config.clone();
        }

    }
}
