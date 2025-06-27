package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BubbleSorter extends AbstractSorter {

    @Override
    protected <T extends Comparable<T>> void doSort(List<T> data, int startIndex, int endIndex) {
        while (true) {
            boolean swapped = false;
            for (int i = startIndex; i < endIndex - 1; i++) {
                if (data.get(i).compareTo(data.get(i + 1)) > 0) {
                    Sorter.swap(data, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
    }

}
