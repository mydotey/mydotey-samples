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
public interface Multiple {

    static int lcm(int... numbers) {
        ObjectExtension.requireNonNull(numbers, "numbers");
        return lcm(Arrays.stream(numbers).boxed().collect(Collectors.toList()));
    }

    static int lcm(List<Integer> numbers) {
        ObjectExtension.requireNonEmpty(numbers, "numbers");

        int number0 = numbers.get(0);
        if (numbers.size() == 1)
            return number0;

        int lcm1 = lcm(numbers.subList(1, numbers.size()));
        return number0 * lcm1 / Divisor.gcd(number0, lcm1);
    }

}
