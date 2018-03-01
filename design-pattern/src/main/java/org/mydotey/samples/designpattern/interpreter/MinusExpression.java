package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class MinusExpression extends AbstractExpression {

    public MinusExpression(String expression) {
        super(expression);
    }

    @Override
    public double caculate() {
        String[] parts = getExpression().split("-");
        return new GeneralExpression(parts[0]).caculate() - new GeneralExpression(parts[1]).caculate();
    }

}
