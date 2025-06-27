package org.mydotey.samples.algorithm.leetcode;

import java.util.Arrays;

public class MinimumEffort {
    public int minimumEffort(int[][] tasks) {
        Arrays.sort(tasks, (t1, t2) -> {
            int diff1 = t1[1] - t1[0];
            int diff2 = t2[1] - t2[0];
            if (diff1 > diff2)
                return -1;
            if (diff1 < diff2)
                return 1;
            return t1[1] > t2[1] ? -1 : (t1[1] == t2[1] ? 0 : 1);
        });
        int total = 0;
        int actualTotal = 0;
        for (int[] task : tasks) {
            System.out.printf("%d, %d\n", task[0], task[1]);
            int diff = task[1] - actualTotal;
            if (diff > 0) {
                total += diff;
                actualTotal += diff;
            }

            actualTotal -= task[0];
        }

        return total;
    }

    public static void main(String[] args) {
        int[][] tasks = new int[][] {
            { 1, 2 }, { 2, 4 }, { 4, 8 }
        };
        int min = new MinimumEffort().minimumEffort(tasks);
        System.out.println(min);
    }

}
