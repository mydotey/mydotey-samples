package org.mydotey.samples.algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BinarySearchTest extends SearchTest {

    @Parameters(name = "{index}: data={0}, item={1}, index={2}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        List<Integer> data;
        Integer item;
        int index;

        data = Arrays.asList(1, 2, 3, 4, 5);
        item = 2;
        index = 1;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1, 2, 3, 4, 5);
        item = 5;
        index = 4;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1, 2, 3, 4, 5);
        item = 1;
        index = 0;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1, 2, 3, 4, 5);
        item = -1;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1, 2, 3, 4, 5);
        item = 6;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1);
        item = 1;
        index = 0;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1);
        item = -1;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList(1);
        item = 2;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        data = Arrays.asList();
        item = 1;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        data = null;
        item = 1;
        index = -1;
        parameterValues.add(new Object[] { data, item, index });

        return parameterValues;
    }

    @Override
    protected Searcher newSearcher() {
        return new BinarySearcher();
    }

}
