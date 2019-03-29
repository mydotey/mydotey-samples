package org.mydotey.samples.algorithm.dynamicprogramming.zeroonebag;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mydotey.samples.algorithm.dynamicprogramming.zeroonebag.BagSelection.Bag;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public abstract class BagSelectionTest {

    @Parameters(name = "{index}: bags={0}, maxWeight={1}, selection={2}")
    public static Collection<Object[]> data() {
        Set<Object[]> parameterValues = new HashSet<>();
        Bag[] bags;
        int maxWeight;
        Set<Integer> selection;

        bags = new Bag[] { new Bag(1, 5) };
        maxWeight = 1;
        selection = new HashSet<>(Arrays.asList(0));
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] { new Bag(1, 5), new Bag(2, 20) };
        maxWeight = 2;
        selection = new HashSet<>(Arrays.asList(1));
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] { new Bag(1, 5), new Bag(2, 20) };
        maxWeight = 3;
        selection = new HashSet<>(Arrays.asList(0, 1));
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] {};
        maxWeight = 3;
        selection = new HashSet<>(Arrays.asList());
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] { new Bag(1, 5), new Bag(2, 20) };
        maxWeight = 5;
        selection = new HashSet<>(Arrays.asList(0, 1));
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] { new Bag(1, 5), new Bag(2, 20), new Bag(1, 1), new Bag(2, 2), new Bag(2, 5), new Bag(5, 5),
                new Bag(10, 1) };
        maxWeight = 5;
        selection = new HashSet<>(Arrays.asList(0, 1, 4));
        parameterValues.add(new Object[] { bags, maxWeight, selection });

        bags = new Bag[] { new Bag(1, 5), new Bag(2, 20), new Bag(1, 1), new Bag(2, 2), new Bag(2, 5), new Bag(5, 40),
                new Bag(10, 1) };
        maxWeight = 5;
        selection = new HashSet<>(Arrays.asList(5));
        parameterValues.add(new Object[] { bags, maxWeight, selection });
        return parameterValues;
    }

    @Parameter(0)
    public Bag[] bags;

    @Parameter(1)
    public int maxWeight;

    @Parameter(2)
    public Set<Integer> selection;

    private BagSelection _bagSelection;

    @Before
    public void setUp() {
        _bagSelection = newBagSelection();
    }

    @Test
    public void selectMostBags() {
        Assert.assertEquals(selection, _bagSelection.select(bags, maxWeight));
    }

    protected abstract BagSelection newBagSelection();

}
