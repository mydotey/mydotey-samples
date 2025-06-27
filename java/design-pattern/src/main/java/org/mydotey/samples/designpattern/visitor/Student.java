package org.mydotey.samples.designpattern.visitor;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class Student extends Person {

    public Student(String name) {
        super(name);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

}
