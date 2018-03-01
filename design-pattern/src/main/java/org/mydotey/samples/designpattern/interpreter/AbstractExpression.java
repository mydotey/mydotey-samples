package org.mydotey.samples.designpattern.interpreter;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public abstract class AbstractExpression implements Expression {

    private String _expression;

    public AbstractExpression(String expression) {
        _expression = expression;
    }

    @Override
    public String getExpression() {
        return _expression;
    }

}
