package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BinarySearchInsertionSorter extends InsertionSorter {

    @Override
    protected <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item) {
        int length = endIndex - startIndex;
        if (length == 0)
            return endIndex;

        int middle = startIndex + length / 2;
        int compareResult = data.get(middle).compareTo(item);
        if (compareResult > 0)
            return search(data, startIndex, middle, item);
        else
            return search(data, middle + 1, endIndex, item);
    }

}
