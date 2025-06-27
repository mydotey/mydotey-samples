package org.mydotey.samples.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class SpaceEfficientMergeSorter extends AbstractSorter {

    @Override
    protected <T extends Comparable<T>> void doSort(List<T> data, int startIndex, int endIndex) {
        List<T> temp = new ArrayList<>(data.size());
        temp.addAll(data);
        doSort(data, temp, startIndex, endIndex);
    }

    protected <T extends Comparable<T>> void doSort(List<T> data, List<T> temp, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        if (length <= 1)
            return;

        int middle = startIndex + length / 2;
        doSort(data, temp, startIndex, middle);
        doSort(data, temp, middle, endIndex);

        merge(data, temp, startIndex, middle, endIndex);
    }

    protected <T extends Comparable<T>> void merge(List<T> data, List<T> temp, int startIndex, int middle,
            int endIndex) {
        for (int i = startIndex; i < endIndex; i++)
            temp.set(i, data.get(i));

        int index = startIndex;
        int i = startIndex;
        int j = middle;
        while (i < middle && j < endIndex) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0)
                data.set(index++, temp.get(i++));
            else
                data.set(index++, temp.get(j++));
        }

        if (i == middle) {
            while (j < endIndex)
                data.set(index++, temp.get(j++));
        } else if (j == endIndex) {
            while (i < middle)
                data.set(index++, temp.get(i++));
        }
    }

}
