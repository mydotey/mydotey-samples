package org.mydotey.samples.designpattern.objectpool.bak.autorefreshed;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.mydotey.samples.designpattern.objectpool.bak.ObjectPoolConfig;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoRefreshedObjectPoolConfig extends ObjectPoolConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

    private long objectTtl;
    private StaleChecker staleChecker;
    private long staleCheckInterval;

    protected AutoRefreshedObjectPoolConfig() {

    }

    public long getObjectTtl() {
        return objectTtl;
    }

    public StaleChecker getStaleChecker() {
        return staleChecker;
    }

    public long getStaleCheckInterval() {
        return staleCheckInterval;
    }

    public static class Builder extends ObjectPoolConfig.Builder {

        protected Builder() {
            getPoolConfig().objectTtl = Long.MAX_VALUE;
            getPoolConfig().staleChecker = StaleChecker.DEFAULT;
            getPoolConfig().staleCheckInterval = TimeUnit.SECONDS.toMillis(1);
        }

        @Override
        protected AutoRefreshedObjectPoolConfig newPoolConfig() {
            return new AutoRefreshedObjectPoolConfig();
        }

        @Override
        protected AutoRefreshedObjectPoolConfig getPoolConfig() {
            return (AutoRefreshedObjectPoolConfig) super.getPoolConfig();
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
        public Builder setScaleFactor(int scaleFactor) {
            return (Builder) super.setScaleFactor(scaleFactor);
        }

        @Override
        public Builder setObjectFactory(Supplier<?> objectFactory) {
            return (Builder) super.setObjectFactory(objectFactory);
        }

        public Builder setObjectTtl(long objectTtl) {
            getPoolConfig().objectTtl = objectTtl;
            return this;
        }

        public Builder setStaleChecker(StaleChecker staleChecker) {
            getPoolConfig().staleChecker = staleChecker;
            return this;
        }

        public Builder setStaleCheckInterval(long staleCheckInterval) {
            getPoolConfig().staleCheckInterval = staleCheckInterval;
            return this;
        }

        @Override
        public AutoRefreshedObjectPoolConfig build() {
            if (getPoolConfig().objectTtl <= 0)
                throw new IllegalStateException("objectTtl is invalid: " + getPoolConfig().objectTtl);

            if (getPoolConfig().staleChecker == null)
                throw new IllegalStateException("staleChecker is null.");

            if (getPoolConfig().staleCheckInterval <= 0)
                throw new IllegalStateException("staleCheckInterval is invalid: " + getPoolConfig().staleCheckInterval);

            return (AutoRefreshedObjectPoolConfig) super.build();
        }

    }

}
