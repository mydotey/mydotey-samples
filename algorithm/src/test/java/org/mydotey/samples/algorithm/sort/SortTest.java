package org.mydotey.samples.algorithm.sort;

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

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
@RunWith(Parameterized.class)
public abstract class SortTest {

    @Parameters(name = "{index}: data={0}, sorted={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        List<Integer> data;
        List<Integer> sorted;

        data = Arrays.asList(1, 2, 3, 4, 5);
        sorted = Arrays.asList(1, 2, 3, 4, 5);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(5, 4, 3, 2, 1);
        sorted = Arrays.asList(1, 2, 3, 4, 5);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(1, 3, 2, 5, 4);
        sorted = Arrays.asList(1, 2, 3, 4, 5);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(2, 1, 3, 4, 5);
        sorted = Arrays.asList(1, 2, 3, 4, 5);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(5, 2, 4, 1, 3);
        sorted = Arrays.asList(1, 2, 3, 4, 5);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(1);
        sorted = Arrays.asList(1);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(1, 2);
        sorted = Arrays.asList(1, 2);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList(2, 1);
        sorted = Arrays.asList(1, 2);
        parameterValues.add(new Object[] { data, sorted });

        data = Arrays.asList();
        sorted = Arrays.asList();
        parameterValues.add(new Object[] { data, sorted });

        return parameterValues;
    }

    @Parameter(0)
    public List<Integer> data;

    @Parameter(1)
    public List<Integer> sorted;

    @Test
    public void sort() {
        Sorter sorter = newSorter();
        System.out.println("data: \n" + data);
        sorter.sort(data);
        System.out.println("sorted: \n" + data);
        System.out.println("expected: \n" + sorted);
        Assert.assertEquals(sorted, data);
    }

    protected abstract Sorter newSorter();

}
