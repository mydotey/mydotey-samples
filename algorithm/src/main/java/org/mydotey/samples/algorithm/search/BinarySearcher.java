package org.mydotey.samples.algorithm.search;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BinarySearcher implements Searcher {

    @Override
    public <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item) {
        Searcher.checkArguments(data, startIndex, endIndex, item);

        int length = endIndex - startIndex;
        if (length == 0)
            return -1;

        int middleIndex = startIndex + length / 2;
        int compareResult = data.get(middleIndex).compareTo(item);
        if (compareResult == 0)
            return middleIndex;
        else if (compareResult > 0)
            return search(data, startIndex, middleIndex, item);
        else
            return search(data, middleIndex + 1, endIndex, item);
    }

}
