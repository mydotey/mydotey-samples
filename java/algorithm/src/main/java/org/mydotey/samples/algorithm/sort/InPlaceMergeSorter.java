package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class InPlaceMergeSorter extends MergeSorter {

    @Override
    protected <T extends Comparable<T>> void merge(List<T> data, int startIndex, int middle, int endIndex) {
        int i = startIndex;
        for (; data.get(i).compareTo(data.get(middle)) <= 0;) {
            i++;
            if (i == middle)
                return;
        }

        int j = middle;
        for (; j < endIndex && data.get(i).compareTo(data.get(j)) > 0; j++)
            ;

        for (int l = middle; l < j; l++) {
            T item = data.get(l);
            for (int m = l; m > i; m--)
                data.set(m, data.get(m - 1));
            data.set(i++, item);
        }

        if (j < endIndex)
            merge(data, i, j, endIndex);
    }

}
