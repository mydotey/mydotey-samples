package org.mydotey.samples.designpattern.state;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class OriginalState implements State {

    @Override
    public String getState() {
        return "origin";
    }

    @Override
    public double getDiscount() {
        return 0;
    }

    @Override
    public int getOff() {
        return 0;
    }

}
