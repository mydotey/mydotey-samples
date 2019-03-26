package org.mydotey.samples.algorithm.backtrack;

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
public class NQueenTest {

    @Parameters(name = "{index}: n={0}, count={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        int n;
        int count;

        n = 4;
        count = 2;
        parameterValues.add(new Object[] { n, count });

        n = 8;
        count = 92;
        parameterValues.add(new Object[] { n, count });

        return parameterValues;
    }

    @Parameter(0)
    public int n;

    @Parameter(1)
    public int count;

    @Test
    public void count() {
        Assert.assertEquals(count, new NQueen().count(n));
    }

}
