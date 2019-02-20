package org.mydotey.samples.algorithm.sort;

import java.util.List;

/**
 * @author koqizhao
 *
 * Feb 20, 2019
 */
public class BubbleSorter implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data) {
        if (data == null)
            return;

        while (true) {
            boolean swapped = false;
            for (int i = 0; i < data.size() - 1; i++) {
                if (data.get(i).compareTo(data.get(i + 1)) > 0) {
                    Sorter.swap(data, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
    }

}
