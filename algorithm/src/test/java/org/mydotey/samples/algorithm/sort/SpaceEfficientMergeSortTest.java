package org.mydotey.samples.algorithm.sort;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
public class SpaceEfficientMergeSortTest extends SortTest {

    @Override
    protected Sorter newSorter() {
        return new SpaceEfficientMergeSorter();
    }

}
