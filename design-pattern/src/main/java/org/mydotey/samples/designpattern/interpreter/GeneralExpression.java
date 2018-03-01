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
        String expression = getExpression().replace(" ", "").replace("-", "+-");
        if (expression.indexOf("+") != -1)
            return new PlusExpression(expression).caculate();

        return new NumberExpression(expression).caculate();
    }

}
