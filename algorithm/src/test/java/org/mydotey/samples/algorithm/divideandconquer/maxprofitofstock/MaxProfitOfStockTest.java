package org.mydotey.samples.algorithm.divideandconquer.maxprofitofstock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mydotey.samples.algorithm.divideandconquer.maxprofitofstock.MaxProfitOfStock.Deal;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public abstract class MaxProfitOfStockTest {

    @Parameters(name = "{index}: prices={0}, solutions={1}")
    public static Collection<Object[]> data() {
        List<Object[]> parameterValues = new ArrayList<>();
        int[] prices;
        List<Deal> solutions;

        prices = new int[] {};
        solutions = Arrays.asList();
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 1 };
        solutions = Arrays.asList();
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 2, 1 };
        solutions = Arrays.asList();
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 1, 1 };
        solutions = Arrays.asList();
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 1, 2 };
        solutions = Arrays.asList(new Deal(0, 1));
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 1, 3, 5, 7 };
        solutions = Arrays.asList(new Deal(0, 3));
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 7, 3, 5, 7 };
        solutions = Arrays.asList(new Deal(1, 3));
        parameterValues.add(new Object[] { prices, solutions });

        prices = new int[] { 7, 1, 5, 1, 7 };
        solutions = Arrays.asList(new Deal(1, 2), new Deal(3, 4));
        parameterValues.add(new Object[] { prices, solutions });

        return parameterValues;
    }

    @Parameter(0)
    public int[] prices;

    @Parameter(1)
    public List<Deal> solutions;

    private MaxProfitOfStock _maxProfitOfStock;

    @Before
    public void setUp() {
        _maxProfitOfStock = newMaxProfitOfStock();
    }

    @Test
    public void calculate() {
        Assert.assertEquals(solutions, _maxProfitOfStock.getSolutions(prices));
    }

    protected abstract MaxProfitOfStock newMaxProfitOfStock();

}
