package org.mydotey.samples.designpattern.visitor;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public abstract class Person {

    private String _name;

    public Person(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public abstract void acceptVisitor(Visitor visitor);

}
