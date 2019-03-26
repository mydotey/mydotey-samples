package org.mydotey.samples.algorithm.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public class GcdTest {

    @Parameters(name = "{index}: numbers={0}, gcd={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        int[] numbers;
        int gcd;

        numbers = new int[] { 1 };
        gcd = 1;
        parameterValues.add(new Object[] { numbers, gcd });

        numbers = new int[] { 3, 5 };
        gcd = 1;
        parameterValues.add(new Object[] { numbers, gcd });

        numbers = new int[] { 3, 6 };
        gcd = 3;
        parameterValues.add(new Object[] { numbers, gcd });

        numbers = new int[] { 8, 6 };
        gcd = 2;
        parameterValues.add(new Object[] { numbers, gcd });

        numbers = new int[] { 12, 9, 15 };
        gcd = 3;
        parameterValues.add(new Object[] { numbers, gcd });

        return parameterValues;
    }

    @Parameter(0)
    public int[] numbers;

    @Parameter(1)
    public int gcd;

    @Test
    public void gcd() {
        Assert.assertEquals(gcd, Divisor.gcd(numbers));
    }

}
