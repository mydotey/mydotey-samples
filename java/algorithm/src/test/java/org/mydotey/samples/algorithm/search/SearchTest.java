package org.mydotey.samples.algorithm.search;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
@RunWith(Parameterized.class)
public abstract class SearchTest {

    @Parameter(0)
    public List<Integer> data;

    @Parameter(1)
    public Integer item;

    @Parameter(2)
    public int index;

    @Test
    public void search() {
        Searcher searcher = newSearcher();
        System.out.println("data: \n" + data);
        System.out.println("item: \n" + item);
        int result = searcher.search(data, item);
        System.out.println("result: \n" + result);
        System.out.println("expected: \n" + index);
        Assert.assertEquals(index, result);
    }

    protected abstract Searcher newSearcher();

}
