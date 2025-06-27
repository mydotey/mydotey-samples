package org.mydotey.samples.algorithm.search;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BinarySearcher extends AbstractSearcher {

    @Override
    protected <T extends Comparable<T>> int doSearch(List<T> data, int startIndex, int endIndex, T item) {
        int length = endIndex - startIndex;
        if (length == 0)
            return -1;

        int middleIndex = startIndex + length / 2;
        int compareResult = data.get(middleIndex).compareTo(item);
        if (compareResult == 0)
            return middleIndex;
        else if (compareResult > 0)
            return doSearch(data, startIndex, middleIndex, item);
        else
            return doSearch(data, middleIndex + 1, endIndex, item);
    }

}
