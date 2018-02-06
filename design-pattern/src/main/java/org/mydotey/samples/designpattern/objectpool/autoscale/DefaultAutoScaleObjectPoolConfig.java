package org.mydotey.samples.designpattern.objectpool.autoscale;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.DefaultObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class DefaultAutoScaleObjectPoolConfig extends DefaultObjectPoolConfig implements AutoScaleObjectPoolConfig {

    private long objectTtl;
    private long maxIdleTime;
    private StaleChecker staleChecker;
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
    public StaleChecker getStaleChecker() {
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

    public static class Builder extends DefaultObjectPoolConfig.Builder implements AutoScaleObjectPoolConfig.Builder {

        public Builder() {
            getPoolConfig().objectTtl = Long.MAX_VALUE;
            getPoolConfig().maxIdleTime = Long.MAX_VALUE;
            getPoolConfig().staleChecker = StaleChecker.DEFAULT;
            getPoolConfig().staleCheckInterval = TimeUnit.SECONDS.toMillis(1);
            getPoolConfig().scaleFactor = 1;
        }

        @Override
        protected DefaultAutoScaleObjectPoolConfig newPoolConfig() {
            return new DefaultAutoScaleObjectPoolConfig();
        }

        @Override
        protected DefaultAutoScaleObjectPoolConfig getPoolConfig() {
            return (DefaultAutoScaleObjectPoolConfig) super.getPoolConfig();
        }

        @Override
        public Builder setMinSize(int minSize) {
            return (Builder) super.setMinSize(minSize);
        }

        @Override
        public Builder setMaxSize(int maxSize) {
            return (Builder) super.setMaxSize(maxSize);
        }

        @Override
        public Builder setObjectFactory(Supplier<?> objectFactory) {
            return (Builder) super.setObjectFactory(objectFactory);
        }

        @Override
        public Builder setObjectTtl(long objectTtl) {
            getPoolConfig().objectTtl = objectTtl;
            return this;
        }

        @Override
        public Builder setMaxIdleTime(long maxIdleTime) {
            getPoolConfig().maxIdleTime = maxIdleTime;
            return this;
        }

        @Override
        public Builder setStaleChecker(StaleChecker staleChecker) {
            getPoolConfig().staleChecker = staleChecker;
            return this;
        }

        @Override
        public Builder setStaleCheckInterval(long staleCheckInterval) {
            getPoolConfig().staleCheckInterval = staleCheckInterval;
            return this;
        }

        @Override
        public Builder setScaleFactor(int scaleFactor) {
            getPoolConfig().scaleFactor = scaleFactor;
            return this;
        }

        @Override
        public DefaultAutoScaleObjectPoolConfig build() {
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

            return (DefaultAutoScaleObjectPoolConfig) super.build();
        }

    }

}
