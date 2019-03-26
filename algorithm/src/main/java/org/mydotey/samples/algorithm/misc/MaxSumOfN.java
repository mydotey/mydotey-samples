package org.mydotey.samples.algorithm.misc;

import java.util.ArrayList;
import java.util.List;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 * 
 * 给定N个数，找出连续的数的和的最大值，以及和为最大值的1个序列
 *
 * Mar 26, 2019
 */
public interface MaxSumOfN {

    static List<Integer> sequenceOfMaxSum(int... numbers) {
        ObjectExtension.requireNonNull(numbers, "numbers");

        int maxSum = Integer.MIN_VALUE;
        List<Integer> maxSumSeq = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            int sum = 0;
            List<Integer> sumSeq = new ArrayList<>();
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                sumSeq.add(numbers[j]);
                if (sum > maxSum || sum == maxSum && sumSeq.size() < maxSumSeq.size()) {
                    maxSum = sum;
                    maxSumSeq = new ArrayList<>(sumSeq);
                }
            }
        }

        return maxSumSeq;
    }
}
