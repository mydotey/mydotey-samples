package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.ObjectPool;
import org.mydotey.samples.designpattern.objectpool.DefaultObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class DefaultAutoScaleObjectPoolConfig<T> extends DefaultObjectPoolConfig<T>
        implements AutoScaleObjectPoolConfig<T> {

    public static <T> Builder<T> newBuilder() {
        return new Builder<T>();
    }

    private long _objectTtl;
    private long _maxIdleTime;
    private StaleChecker<T> _staleChecker;
    private long _checkInterval;
    private int _scaleFactor;

    protected DefaultAutoScaleObjectPoolConfig() {

    }

    @Override
    public long getObjectTtl() {
        return _objectTtl;
    }

    @Override
    public long getMaxIdleTime() {
        return _maxIdleTime;
    }

    @Override
    public StaleChecker<T> getStaleChecker() {
        return _staleChecker;
    }

    @Override
    public long getCheckInterval() {
        return _checkInterval;
    }

    @Override
    public int getScaleFactor() {
        return _scaleFactor;
    }

    public static class Builder<T> extends DefaultObjectPoolConfig.Builder<T>
            implements AutoScaleObjectPoolConfig.Builder<T> {

        @SuppressWarnings("unchecked")
        protected Builder() {
            getPoolConfig()._objectTtl = Long.MAX_VALUE;
            getPoolConfig()._maxIdleTime = Long.MAX_VALUE;
            getPoolConfig()._staleChecker = StaleChecker.DEFAULT;
            getPoolConfig()._checkInterval = TimeUnit.SECONDS.toMillis(1);
            getPoolConfig()._scaleFactor = 1;
        }

        @Override
        protected DefaultAutoScaleObjectPoolConfig<T> newPoolConfig() {
            return new DefaultAutoScaleObjectPoolConfig<T>();
        }

        @Override
        protected DefaultAutoScaleObjectPoolConfig<T> getPoolConfig() {
            return (DefaultAutoScaleObjectPoolConfig<T>) super.getPoolConfig();
        }

        @Override
        public Builder<T> setMinSize(int minSize) {
            return (Builder<T>) super.setMinSize(minSize);
        }

        @Override
        public Builder<T> setMaxSize(int maxSize) {
            return (Builder<T>) super.setMaxSize(maxSize);
        }

        @Override
        public Builder<T> setObjectFactory(Supplier<T> objectFactory) {
            return (Builder<T>) super.setObjectFactory(objectFactory);
        }

        @Override
        public Builder<T> setOnCreate(Consumer<ObjectPool.Entry<T>> onEntryCreate) {
            return (Builder<T>) super.setOnCreate(onEntryCreate);
        }

        @Override
        public Builder<T> setOnClose(Consumer<ObjectPool.Entry<T>> onClose) {
            return (Builder<T>) super.setOnClose(onClose);
        }

        @Override
        public Builder<T> setObjectTtl(long objectTtl) {
            getPoolConfig()._objectTtl = objectTtl;
            return this;
        }

        @Override
        public Builder<T> setMaxIdleTime(long maxIdleTime) {
            getPoolConfig()._maxIdleTime = maxIdleTime;
            return this;
        }

        @Override
        public Builder<T> setStaleChecker(StaleChecker<T> staleChecker) {
            getPoolConfig()._staleChecker = staleChecker;
            return this;
        }

        @Override
        public Builder<T> setCheckInterval(long checkInterval) {
            getPoolConfig()._checkInterval = checkInterval;
            return this;
        }

        @Override
        public Builder<T> setScaleFactor(int scaleFactor) {
            getPoolConfig()._scaleFactor = scaleFactor;
            return this;
        }

        @Override
        public DefaultAutoScaleObjectPoolConfig<T> build() {
            if (getPoolConfig()._objectTtl <= 0)
                throw new IllegalStateException("objectTtl is invalid: " + getPoolConfig()._objectTtl);

            if (getPoolConfig()._maxIdleTime <= 0)
                throw new IllegalStateException("maxIdleTime is invalid: " + getPoolConfig()._maxIdleTime);

            if (getPoolConfig()._staleChecker == null)
                throw new IllegalStateException("staleChecker is null.");

            if (getPoolConfig()._checkInterval <= 0)
                throw new IllegalStateException("checkInterval is invalid: " + getPoolConfig()._checkInterval);

            if (getPoolConfig()._scaleFactor <= 0)
                throw new IllegalStateException("invalid scaleFactor: " + getPoolConfig()._scaleFactor);

            if (getPoolConfig()._scaleFactor > getPoolConfig().getMaxSize() - getPoolConfig().getMinSize())
                throw new IllegalStateException("too large scaleFactor: " + getPoolConfig()._scaleFactor);

            return (DefaultAutoScaleObjectPoolConfig<T>) super.build();
        }

    }

}
