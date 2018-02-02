package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ReusablePoolConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

    private int minSize;
    private int maxSize;
    private int scaleFactor;

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

    @Override
    protected ReusablePoolConfig clone() {
        ReusablePoolConfig config = new ReusablePoolConfig();
        config.minSize = minSize;
        config.maxSize = maxSize;
        config.scaleFactor = scaleFactor;
        return config;
    }

    public static class Builder {

        private ReusablePoolConfig _config;

        private Builder() {

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

        public ReusablePoolConfig build() {
            return _config.clone();
        }

    }
}
