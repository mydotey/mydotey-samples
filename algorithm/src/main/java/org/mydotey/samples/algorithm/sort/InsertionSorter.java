package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class InsertionSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data) {
        if (data == null)
            return;

        sort(data, 0, data.size());
    }

    protected <T extends Comparable<T>> void sort(List<T> data, int startIndex, int endIndex) {
        if (endIndex - startIndex <= 1)
            return;

        int lastIndex = endIndex - 1;
        T lastItem = data.get(lastIndex);
        sort(data, startIndex, lastIndex);
        for (int i = startIndex; i < lastIndex; i++) {
            if (data.get(i).compareTo(lastItem) > 0) {
                for (int j = lastIndex; j > i; j--)
                    data.set(j, data.get(j - 1));

                data.set(i, lastItem);
                break;
            }
        }
    }

}
