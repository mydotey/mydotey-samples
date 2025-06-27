package org.mydotey.samples.designpattern.bridge;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class Bridge implements Abstraction {

    SubAbstractionA _subAbstractionA;

    SubAbstractionB _subAbstractionB;

    SubAbstractionC _subAbstractionC;

    public Bridge(SubAbstractionA subAbstractionA, SubAbstractionB subAbstractionB, SubAbstractionC subAbstractionC) {
        _subAbstractionA = subAbstractionA;
        _subAbstractionB = subAbstractionB;
        _subAbstractionC = subAbstractionC;
    }

    @Override
    public void doA() {
        _subAbstractionA.doA();
    }

    @Override
    public void doB() {
        _subAbstractionB.doB();
    }

    @Override
    public void doC() {
        _subAbstractionC.doC();
    }

}
