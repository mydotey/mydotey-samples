package org.mydotey.samples.algorithm.search;

import java.util.List;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public interface Searcher {

    default <T extends Comparable<T>> int search(List<T> data, T item) {
        return search(data, 0, data.size(), item);
    }

    <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item);

    static <T extends Comparable<T>> void checkArguments(List<T> data, int startIndex, int endIndex, T item) {
        ObjectExtension.requireNonNull(data, "data");
        ObjectExtension.requireNonNull(item, "item");

        if (startIndex < 0 || startIndex >= data.size())
            throw new IndexOutOfBoundsException("startIndex");

        if (endIndex < 0 || endIndex >= data.size())
            throw new IndexOutOfBoundsException("endIndex");
    }

}
