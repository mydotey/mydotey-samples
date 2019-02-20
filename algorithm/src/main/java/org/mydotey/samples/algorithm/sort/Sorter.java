package org.mydotey.samples.algorithm.sort;

import java.util.List;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
public interface Sorter {

    <T extends Comparable<T>> void sort(List<T> data);

    static <T> void swap(List<T> data, int i, int j) {
        ObjectExtension.requireNonNull(data, "data");
        if (i < 0 || i >= data.size())
            throw new IndexOutOfBoundsException("i");
        if (j < 0 || j >= data.size())
            throw new IndexOutOfBoundsException("j");

        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

}
