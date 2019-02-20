package org.mydotey.samples.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class MergeSorter extends AbstractSorter {

    @Override
    protected <T extends Comparable<T>> void doSort(List<T> data, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        if (length <= 1)
            return;

        int middle = startIndex + length / 2;
        doSort(data, startIndex, middle);
        doSort(data, middle, endIndex);

        merge(data, startIndex, middle, endIndex);
    }

    protected <T extends Comparable<T>> void merge(List<T> data, int startIndex, int middle, int endIndex) {
        List<T> left = new ArrayList<>(middle - startIndex);
        List<T> right = new ArrayList<>(endIndex - middle);
        for (int i = startIndex; i < endIndex; i++) {
            if (i < middle)
                left.add(data.get(i));
            else
                right.add(data.get(i));
        }

        int index = startIndex;
        int i, j;
        i = j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0)
                data.set(index++, left.get(i++));
            else
                data.set(index++, right.get(j++));
        }

        if (i == left.size()) {
            while (j < right.size())
                data.set(index++, right.get(j++));
        } else if (j == right.size()) {
            while (i < left.size())
                data.set(index++, left.get(i++));
        }
    }

}
