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
    private Supplier<?> reusableFactory;

    private ObjectPoolConfig() {

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

    public Supplier<?> getReusableFactory() {
        return reusableFactory;
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

        private ObjectPoolConfig _config;

        private Builder() {
            _config = new ObjectPoolConfig();
            _config.scaleFactor = 1;
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

        public Builder setReusableFactory(Supplier<?> reusableFactory) {
            _config.reusableFactory = reusableFactory;
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

            if (_config.reusableFactory == null)
                throw new IllegalStateException("reusableFactory is not set");

            return _config.clone();
        }

    }
}
