package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class DefaultObjectPoolConfig implements ObjectPoolConfig, Cloneable {

    public static Builder newBuilder() {
        return new Builder();
    }

    private int minSize;
    private int maxSize;
    private Supplier<?> objectFactory;

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
    public Supplier<?> getObjectFactory() {
        return objectFactory;
    }

    @Override
    protected DefaultObjectPoolConfig clone() {
        try {
            return (DefaultObjectPoolConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static class Builder implements ObjectPoolConfig.Builder {

        protected DefaultObjectPoolConfig _config;

        protected Builder() {
            _config = newPoolConfig();
        }

        protected DefaultObjectPoolConfig newPoolConfig() {
            return new DefaultObjectPoolConfig();
        }

        protected DefaultObjectPoolConfig getPoolConfig() {
            return _config;
        }

        @Override
        public Builder setMinSize(int minSize) {
            _config.minSize = minSize;
            return this;
        }

        @Override
        public Builder setMaxSize(int maxSize) {
            _config.maxSize = maxSize;
            return this;
        }

        @Override
        public Builder setObjectFactory(Supplier<?> objectFactory) {
            _config.objectFactory = objectFactory;
            return this;
        }

        @Override
        public DefaultObjectPoolConfig build() {
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
