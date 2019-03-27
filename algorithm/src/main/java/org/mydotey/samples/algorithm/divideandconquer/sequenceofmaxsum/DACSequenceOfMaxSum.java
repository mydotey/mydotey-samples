package org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 * 
 * Mar 26, 2019
 */
public class DACSequenceOfMaxSum implements SequenceOfMaxSum {

    @Override
    public Sequence calculate(int... numbers) {
        ObjectExtension.requireNonNull(numbers, "numbers");

        return calculate(numbers, numbers.length);
    }

    protected Sequence calculate(int[] numbers, int length) {
        if (length <= 1)
            return new Sequence(0, length);

        Sequence maxSeq = calculate(numbers, length - 1);
        int endIndex = maxSeq.getStart() + maxSeq.getLength();
        int maxSum = 0;
        for (int i = maxSeq.getStart(); i < endIndex; i++)
            maxSum += numbers[i];

        int rightMaxSum = Integer.MIN_VALUE;
        Sequence rightMaxSeq = null;
        int rightSum = 0;
        for (int i = length - 1; i >= endIndex; i--) {
            rightSum += numbers[i];
            if (rightSum > rightMaxSum) {
                rightMaxSum = rightSum;
                rightMaxSeq = new Sequence(i, length - i);
            }
        }

        if (rightSum > 0 && rightSum + maxSum > rightMaxSum)
            return new Sequence(maxSeq.getStart(), length - maxSeq.getStart());

        return rightMaxSum > maxSum ? rightMaxSeq : maxSeq;
    }

}
