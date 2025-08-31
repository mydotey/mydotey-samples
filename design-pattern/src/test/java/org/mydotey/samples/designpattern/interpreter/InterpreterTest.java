package org.mydotey.samples.designpattern.interpreter;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class InterpreterTest {

    @Test
    public void interpreterTest() {
        interprete("10");
        interprete("10 + 5");
        interprete("10 + 5 - 3");
        interprete("10 + 5 - 2 + 9");
        interprete("5 - 10 + 1");
        interprete("5 - 5 + 5 - 5");
    }

    private void interprete(String expression) {
        double result = new GeneralExpression(expression).caculate();
        System.out.println("expression: " + expression);
        System.out.println("result: " + result);
        System.out.println();
    }

}
