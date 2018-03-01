package org.mydotey.samples.designpattern.visitor;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteVisitor implements Visitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("teacher: " + teacher.getName());
    }

    @Override
    public void visit(Student student) {
        System.out.println("student: " + student.getName());
    }

}
