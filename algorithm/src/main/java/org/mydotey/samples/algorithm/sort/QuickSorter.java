package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
public class QuickSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data) {
        if (data == null)
            return;

        sort(data, 0, data.size());
    }

    protected <T extends Comparable<T>> void sort(List<T> data, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        if (length <= 1)
            return;

        int pivot = startIndex + length / 2;
        T item = data.get(pivot);
        for (int i = startIndex, j = endIndex - 1; i < j;) {
            while (i < pivot && data.get(i).compareTo(item) <= 0) {
                i++;
            }

            data.set(pivot, data.get(i));
            pivot = i;

            while (j > pivot && data.get(j).compareTo(item) >= 0) {
                j--;
            }

            data.set(pivot, data.get(j));
            pivot = j;
        }

        data.set(pivot, item);

        sort(data, startIndex, pivot);
        sort(data, pivot + 1, endIndex);
    }

}
