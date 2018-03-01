package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class NumberExpression extends AbstractExpression {

    public NumberExpression(String expression) {
        super(expression);
    }

    @Override
    public double caculate() {
        return Double.parseDouble(getExpression());
    }

}
