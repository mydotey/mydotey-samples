package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class GeneralExpression extends AbstractExpression {

    public GeneralExpression(String expression) {
        super(expression);
    }

    @Override
    public double caculate() {
        String expression = getExpression();
        if (expression.indexOf("+") != -1)
            return new PlusExpression(getExpression()).caculate();

        if (expression.indexOf("-") != -1)
            return new MinusExpression(getExpression()).caculate();

        return new Number(expression).caculate();
    }

}
