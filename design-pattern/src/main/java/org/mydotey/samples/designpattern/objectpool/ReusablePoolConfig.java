package org.mydotey.samples.designpattern.objectpool;

import java.util.function.Supplier;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
@SuppressWarnings("unchecked")
public class ReusablePoolConfig<T extends Reusable<T>> implements Cloneable {

    public static <T extends Reusable<T>> Builder<T> newBuilder() {
        return new Builder<T>();
    }

    private int minSize;
    private int maxSize;
    private int scaleFactor;
    private Supplier<Reusable<T>> reusableFactory;

    private ReusablePoolConfig() {

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

    public Supplier<Reusable<T>> getReusableFactory() {
        return reusableFactory;
    }

    @Override
    protected ReusablePoolConfig<T> clone() {
        try {
            return (ReusablePoolConfig<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static class Builder<T extends Reusable<T>> {

        private ReusablePoolConfig<T> _config;

        private Builder() {
            _config = new ReusablePoolConfig<T>();
            _config.scaleFactor = 1;
        }

        public Builder<T> setMinSize(int minSize) {
            _config.minSize = minSize;
            return this;
        }

        public Builder<T> setMaxSize(int maxSize) {
            _config.maxSize = maxSize;
            return this;
        }

        public Builder<T> setScaleFactor(int scaleFactor) {
            _config.scaleFactor = scaleFactor;
            return this;
        }

        public Builder<T> setReusableFactory(Supplier<Reusable<T>> reusableFactory) {
            _config.reusableFactory = reusableFactory;
            return this;
        }

        public ReusablePoolConfig<T> build() {
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
