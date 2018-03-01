package org.mydotey.samples.designpattern.templatemethod;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteProduct extends Product {

    @Override
    protected void showProductSteps() {
        System.out.println("\tstep 1: ");
        System.out.println("\tstep 2: ");
        System.out.println("\tstep 3: ");
    }

}
