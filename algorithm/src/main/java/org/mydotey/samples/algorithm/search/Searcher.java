package org.mydotey.samples.algorithm.search;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public interface Searcher {

    <T extends Comparable<T>> int search(List<T> data, T item);

}
