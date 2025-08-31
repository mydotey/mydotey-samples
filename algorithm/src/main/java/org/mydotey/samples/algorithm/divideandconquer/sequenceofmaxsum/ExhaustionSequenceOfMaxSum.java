package org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 * 
 * Mar 26, 2019
 */
public class ExhaustionSequenceOfMaxSum implements SequenceOfMaxSum {

    @Override
    public Sequence calculate(int... numbers) {
        ObjectExtension.requireNonNull(numbers, "numbers");

        int maxSum = Integer.MIN_VALUE;
        Sequence maxSeq = new Sequence(0, 0);
        for (int i = 0; i < numbers.length; i++) {
            int sum = 0;
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                if (sum > maxSum || sum == maxSum && j - i + 1 < maxSeq.getLength()) {
                    maxSum = sum;
                    maxSeq = new Sequence(i, j - i + 1);
                }
            }
        }

        return maxSeq;
    }
}
