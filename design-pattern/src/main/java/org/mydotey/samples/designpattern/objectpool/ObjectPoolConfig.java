package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPoolConfig implements Cloneable {

    public static Builder newBuilder() {
        return new Builder();
    }

    private int minSize;
    private int maxSize;
    private int scaleFactor;
    private Supplier<?> objectFactory;

    protected ObjectPoolConfig() {

    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getScaleFactor() {
        return scaleFactor;
    }

    public Supplier<?> getObjectFactory() {
        return objectFactory;
    }

    @Override
    protected ObjectPoolConfig clone() {
        try {
            return (ObjectPoolConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static class Builder {

        protected ObjectPoolConfig _config;

        protected Builder() {
            _config = newPoolConfig();
            _config.scaleFactor = 1;
        }

        protected ObjectPoolConfig newPoolConfig() {
            return new ObjectPoolConfig();
        }

        protected ObjectPoolConfig getPoolConfig() {
            return _config;
        }

        public Builder setMinSize(int minSize) {
            _config.minSize = minSize;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            _config.maxSize = maxSize;
            return this;
        }

        public Builder setScaleFactor(int scaleFactor) {
            _config.scaleFactor = scaleFactor;
            return this;
        }

        public Builder setObjectFactory(Supplier<?> objectFactory) {
            _config.objectFactory = objectFactory;
            return this;
        }

        public ObjectPoolConfig build() {
            if (_config.minSize < 0)
                throw new IllegalStateException("minSize is invalid: " + _config.minSize);

            if (_config.maxSize <= 0)
                throw new IllegalStateException("maxSize is invalid: " + _config.maxSize);

            if (_config.minSize > _config.maxSize)
                throw new IllegalStateException(
                        "minSize is larger than maxSiz. minSize: " + _config.minSize + ", maxSize: " + _config.maxSize);

            if (_config.scaleFactor <= 0)
                throw new IllegalStateException("invalid scaleFactor: " + _config.scaleFactor);

            if (_config.scaleFactor > _config.maxSize - _config.minSize)
                throw new IllegalStateException("too large scaleFactor: " + _config.scaleFactor);

            if (_config.objectFactory == null)
                throw new IllegalStateException("objectFactory is not set");

            return _config.clone();
        }

    }
}
