package org.mydotey.samples.algorithm.sort;

import java.util.List;

import org.mydotey.java.collection.CollectionExtension;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public abstract class AbstractSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data, int startIndex, int endIndex) {
        if (CollectionExtension.isEmpty(data))
            return;

        if (startIndex < 0 || startIndex >= data.size())
            throw new IndexOutOfBoundsException("startIndex: " + startIndex);

        if (endIndex < 0 || endIndex > data.size())
            throw new IndexOutOfBoundsException("endIndex: " + endIndex);

        doSort(data, startIndex, endIndex);
    }

    protected abstract <T extends Comparable<T>> void doSort(List<T> data, int startIndex, int endIndex);

}
