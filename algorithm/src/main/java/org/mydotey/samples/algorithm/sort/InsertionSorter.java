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
        int index = search(data, startIndex, lastIndex, lastItem);
        for (int j = lastIndex; j > index; j--)
            data.set(j, data.get(j - 1));
        data.set(index, lastItem);
    }

    protected <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item) {
        for (int i = startIndex; i < endIndex; i++) {
            if (data.get(i).compareTo(item) > 0)
                return i;
        }

        return endIndex;
    }

}
