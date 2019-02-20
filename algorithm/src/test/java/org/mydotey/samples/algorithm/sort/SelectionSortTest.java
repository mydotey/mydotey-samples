package org.mydotey.samples.algorithm.sort;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
public class SelectionSortTest extends SortTest {

    @Override
    protected Sorter newSorter() {
        return new SelectionSorter();
    }

}
