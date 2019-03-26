package org.mydotey.samples.algorithm.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mydotey.samples.algorithm.misc.MaxSumOfN;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public class MaxSumOfNTest {

    @Parameters(name = "{index}: numbers={0}, seq={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        int[] numbers;
        List<Integer> seq;

        numbers = new int[] {};
        seq = Arrays.asList();
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 1 };
        seq = Arrays.asList(1);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 1, 2, 3 };
        seq = Arrays.asList(1, 2, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3 };
        seq = Arrays.asList(1, 2, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3, -1 };
        seq = Arrays.asList(1, 2, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3, 0 };
        seq = Arrays.asList(1, 2, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, 2, 3, 0 };
        seq = Arrays.asList(1, 2, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, -2, 3, 0 };
        seq = Arrays.asList(3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, -2, -1, 1, -1, 9, -3, 3, 5, -2, 10, 0 };
        seq = Arrays.asList(9, -3, 3, 5, -2, 10);
        parameterValues.add(new Object[] { numbers, seq });

        return parameterValues;
    }

    @Parameter(0)
    public int[] numbers;

    @Parameter(1)
    public List<Integer> seq;

    @Test
    public void gcd() {
        Assert.assertEquals(seq, MaxSumOfN.sequenceOfMaxSum(numbers));
    }

}
