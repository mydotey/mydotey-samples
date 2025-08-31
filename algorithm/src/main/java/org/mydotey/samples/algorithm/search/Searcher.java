package org.mydotey.samples.algorithm.search;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public interface Searcher {

    default <T extends Comparable<T>> int search(List<T> data, T item) {
        if (data == null)
            return -1;

        return search(data, 0, data.size(), item);
    }

    <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item);

}
