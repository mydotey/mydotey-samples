package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 19, 2019
 */
public interface Sorter {

    <T extends Comparable<T>> void sort(List<T> data);

}
