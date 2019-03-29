package org.mydotey.samples.algorithm.dynamicprogramming.zeroonebag;

/**
 * @author koqizhao
 *
 * Mar 29, 2019
 */
public class DPBagSelectionTest extends BagSelectionTest {

    @Override
    protected BagSelection newBagSelection() {
        return new DPBagSelection();
    }

}
