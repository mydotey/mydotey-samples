package org.mydotey.samples.algorithm.search;

import java.util.List;

import org.mydotey.java.ObjectExtension;
import org.mydotey.java.collection.CollectionExtension;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public abstract class AbstractSearcher implements Searcher {

    @Override
    public <T extends Comparable<T>> int search(List<T> data, int startIndex, int endIndex, T item) {
        if (CollectionExtension.isEmpty(data))
            return -1;

        if (startIndex < 0 || startIndex >= data.size())
            throw new IndexOutOfBoundsException("startIndex: " + startIndex);

        if (endIndex < 0 || endIndex > data.size())
            throw new IndexOutOfBoundsException("endIndex: " + endIndex);

        ObjectExtension.requireNonNull(item, "item");

        return doSearch(data, startIndex, endIndex, item);
    }

    protected abstract <T extends Comparable<T>> int doSearch(List<T> data, int startIndex, int endIndex, T item);

}
