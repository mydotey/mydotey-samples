package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class SelectionSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data) {
        if (data == null)
            return;

        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = i; j < data.size(); j++) {
                if (data.get(i).compareTo(data.get(j)) > 0)
                    Sorter.swap(data, i, j);
            }
        }
    }

}
