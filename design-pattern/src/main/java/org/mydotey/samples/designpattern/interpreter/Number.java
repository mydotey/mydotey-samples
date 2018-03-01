package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class Number extends AbstractExpression {

    public Number(String expression) {
        super(expression);
    }

    @Override
    public double caculate() {
        return Double.parseDouble(getExpression());
    }

}
