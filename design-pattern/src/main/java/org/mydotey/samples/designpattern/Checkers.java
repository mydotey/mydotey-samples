package org.mydotey.samples.designpattern;

/**
 * @author koqizhao
 *
 *         Feb 1, 2018
 */
public class Checkers {

    private Checkers() {

    }

    public static void requireNonNull(Object obj, String messagePattern, Object... messageParametters) {
        if (obj == null)
            throw new IllegalArgumentException(String.format(messagePattern, messageParametters));
    }

}
