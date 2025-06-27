package org.mydotey.samples.algorithm.math;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Mar 26, 2019
 */
public interface Divisor {

    static int gcd(int... numbers) {
        ObjectExtension.requireNonNull(numbers, "numbers");
        return gcd(Arrays.stream(numbers).boxed().collect(Collectors.toList()));
    }

    static int gcd(List<Integer> numbers) {
        ObjectExtension.requireNonEmpty(numbers, "numbers");

        int min = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < min)
                min = numbers.get(i);
        }

        int divisor = min;
        for (; divisor >= 1; divisor--) {
            boolean divided = true;
            for (int number : numbers) {
                if (number % divisor != 0) {
                    divided = false;
                    break;
                }
            }

            if (divided)
                break;
        }

        return divisor;
    }

}
