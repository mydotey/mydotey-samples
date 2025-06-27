package org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class ExhaustionSequenceOfMaxSumTest extends SequenceOfMaxSumTest {

    @Override
    protected SequenceOfMaxSum newSequenceOfMaxSum() {
        return new ExhaustionSequenceOfMaxSum();
    }

}
