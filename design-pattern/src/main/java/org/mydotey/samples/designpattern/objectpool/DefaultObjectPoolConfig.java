package org.mydotey.samples.designpattern.objectpool;

import java.io.Closeable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPool.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Consumer<Entry<T>> onEntryCreate;
    private Consumer<T> onClose;

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

    @Override
    public Consumer<Entry<T>> getOnEntryCreate() {
        return onEntryCreate;
    }

    @Override
    public Consumer<T> getOnClose() {
        return onClose;
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

        private static Logger _logger = LoggerFactory.getLogger(ObjectPool.class);

        @SuppressWarnings("rawtypes")
        protected static final Consumer DEFAULT_ON_ENTRY_CREATE = e -> {
        };

        @SuppressWarnings("rawtypes")
        protected static final Consumer DEFAULT_ON_CLOSE = o -> {
            if (o instanceof Closeable) {
                try {
                    ((Closeable) o).close();
                } catch (Exception e) {
                    _logger.error("close object failed", e);
                }
            }
        };

        protected DefaultObjectPoolConfig<T> _config;

        @SuppressWarnings("unchecked")
        protected Builder() {
            _config = newPoolConfig();
            _config.onEntryCreate = DEFAULT_ON_ENTRY_CREATE;
            _config.onClose = DEFAULT_ON_CLOSE;
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
        public Builder<T> setOnEntryCreate(Consumer<Entry<T>> onEntryCreate) {
            _config.onEntryCreate = onEntryCreate;
            return this;
        }

        @Override
        public Builder<T> setOnClose(Consumer<T> onClose) {
            _config.onClose = onClose;
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

            if (_config.onEntryCreate == null)
                throw new IllegalStateException("onEntryCreate is null");

            if (_config.onClose == null)
                throw new IllegalStateException("onClose is null");

            return _config.clone();
        }

    }

}
