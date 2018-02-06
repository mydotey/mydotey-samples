package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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

    private long objectTtl;
    private long maxIdleTime;
    private StaleChecker<T> staleChecker;
    private long staleCheckInterval;
    private int scaleFactor;

    protected DefaultAutoScaleObjectPoolConfig() {

    }

    @Override
    public long getObjectTtl() {
        return objectTtl;
    }

    @Override
    public long getMaxIdleTime() {
        return maxIdleTime;
    }

    @Override
    public StaleChecker<T> getStaleChecker() {
        return staleChecker;
    }

    @Override
    public long getStaleCheckInterval() {
        return staleCheckInterval;
    }

    @Override
    public int getScaleFactor() {
        return scaleFactor;
    }

    public static class Builder<T> extends DefaultObjectPoolConfig.Builder<T>
            implements AutoScaleObjectPoolConfig.Builder<T> {

        @SuppressWarnings("unchecked")
        protected Builder() {
            getPoolConfig().objectTtl = Long.MAX_VALUE;
            getPoolConfig().maxIdleTime = Long.MAX_VALUE;
            getPoolConfig().staleChecker = StaleChecker.DEFAULT;
            getPoolConfig().staleCheckInterval = TimeUnit.SECONDS.toMillis(1);
            getPoolConfig().scaleFactor = 1;
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
        public Builder<T> setObjectTtl(long objectTtl) {
            getPoolConfig().objectTtl = objectTtl;
            return this;
        }

        @Override
        public Builder<T> setMaxIdleTime(long maxIdleTime) {
            getPoolConfig().maxIdleTime = maxIdleTime;
            return this;
        }

        @Override
        public Builder<T> setStaleChecker(StaleChecker<T> staleChecker) {
            getPoolConfig().staleChecker = staleChecker;
            return this;
        }

        @Override
        public Builder<T> setStaleCheckInterval(long staleCheckInterval) {
            getPoolConfig().staleCheckInterval = staleCheckInterval;
            return this;
        }

        @Override
        public Builder<T> setScaleFactor(int scaleFactor) {
            getPoolConfig().scaleFactor = scaleFactor;
            return this;
        }

        @Override
        public DefaultAutoScaleObjectPoolConfig<T> build() {
            if (getPoolConfig().objectTtl <= 0)
                throw new IllegalStateException("objectTtl is invalid: " + getPoolConfig().objectTtl);

            if (getPoolConfig().maxIdleTime <= 0)
                throw new IllegalStateException("maxIdleTime is invalid: " + getPoolConfig().maxIdleTime);

            if (getPoolConfig().staleChecker == null)
                throw new IllegalStateException("staleChecker is null.");

            if (getPoolConfig().staleCheckInterval <= 0)
                throw new IllegalStateException("staleCheckInterval is invalid: " + getPoolConfig().staleCheckInterval);

            if (getPoolConfig().scaleFactor <= 0)
                throw new IllegalStateException("invalid scaleFactor: " + getPoolConfig().scaleFactor);

            if (getPoolConfig().scaleFactor > getPoolConfig().getMaxSize() - getPoolConfig().getMinSize())
                throw new IllegalStateException("too large scaleFactor: " + getPoolConfig().scaleFactor);

            return (DefaultAutoScaleObjectPoolConfig<T>) super.build();
        }

    }

}
