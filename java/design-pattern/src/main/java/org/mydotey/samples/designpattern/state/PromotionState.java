package org.mydotey.samples.designpattern.state;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class PromotionState implements State {

    @Override
    public String getState() {
        return "promotion";
    }

    @Override
    public double getDiscount() {
        return 0.25;
    }

    @Override
    public int getOff() {
        return 10;
    }

}
