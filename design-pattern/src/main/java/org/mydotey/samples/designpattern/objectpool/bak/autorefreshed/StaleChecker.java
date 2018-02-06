package org.mydotey.samples.designpattern.objectpool.bak.autorefreshed;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public interface StaleChecker {

    static StaleChecker DEFAULT = new StaleChecker() {

        @Override
        public boolean isStale(Object obj) {
            return false;
        }

    };

    boolean isStale(Object obj);

}
