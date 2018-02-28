package org.mydotey.samples.designpattern.bridge;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class BridgeTest {

    @Test
    public void bridgeTest() {
        SubAbstractionA subAbstractionA = new SubAbstractionA() {
            @Override
            public void doA() {
                System.out.println("I'm a concrete SubAbstractionA. I'm doing A.");
            }
        };
        SubAbstractionB subAbstractionB = new SubAbstractionB() {
            @Override
            public void doB() {
                System.out.println("I'm a concrete SubAbstractionB. I'm doing B.");
            }
        };
        SubAbstractionC subAbstractionC = new SubAbstractionC() {
            @Override
            public void doC() {
                System.out.println("I'm a concrete SubAbstractionC. I'm doing C.");
            }
        };

        Abstraction abstraction = new Bridge(subAbstractionA, subAbstractionB, subAbstractionC);
        abstraction.doA();
        abstraction.doB();
        abstraction.doC();
    }
}
