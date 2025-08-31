package org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum.SequenceOfMaxSum.Sequence;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public abstract class SequenceOfMaxSumTest {

    @Parameters(name = "{index}: numbers={0}, seq={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        int[] numbers;
        Sequence seq;

        numbers = new int[] {};
        seq = new Sequence(0, 0);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 1 };
        seq = new Sequence(0, 1);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 1, 2, 3 };
        seq = new Sequence(0, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3 };
        seq = new Sequence(1, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3, -1 };
        seq = new Sequence(1, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { -1, 1, 2, 3, 0 };
        seq = new Sequence(1, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, 2, 3, 0 };
        seq = new Sequence(1, 3);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, -2, 3, 0 };
        seq = new Sequence(3, 1);
        parameterValues.add(new Object[] { numbers, seq });

        numbers = new int[] { 0, 1, -2, -1, 1, -1, 9, -3, 3, 5, -2, 10, 0 };
        seq = new Sequence(6, 6);
        parameterValues.add(new Object[] { numbers, seq });

        return parameterValues;
    }

    @Parameter(0)
    public int[] numbers;

    @Parameter(1)
    public Sequence seq;

    private SequenceOfMaxSum _sequenceOfMaxSum;

    @Before
    public void setUp() {
        _sequenceOfMaxSum = newSequenceOfMaxSum();
    }

    @Test
    public void calculate() {
        Assert.assertEquals(seq, _sequenceOfMaxSum.calculate(numbers));
    }

    protected abstract SequenceOfMaxSum newSequenceOfMaxSum();

}
