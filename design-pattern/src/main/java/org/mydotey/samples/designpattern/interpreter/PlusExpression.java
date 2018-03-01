package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class PlusExpression extends AbstractExpression {

    public PlusExpression(String expression) {
        super(expression);
    }

    @Override
    public double caculate() {
        String[] parts = getExpression().split("\\+");
        double result = 0;
        for (String part : parts) {
            result += new NumberExpression(part).caculate();
        }

        return result;
    }

}
