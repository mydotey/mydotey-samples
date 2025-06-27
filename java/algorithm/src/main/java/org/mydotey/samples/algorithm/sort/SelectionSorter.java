package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class SelectionSorter extends AbstractSorter {

    @Override
    protected <T extends Comparable<T>> void doSort(List<T> data, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex - 1; i++) {
            for (int j = i + 1; j < endIndex; j++) {
                if (data.get(i).compareTo(data.get(j)) > 0)
                    Sorter.swap(data, i, j);
            }
        }
    }

}
