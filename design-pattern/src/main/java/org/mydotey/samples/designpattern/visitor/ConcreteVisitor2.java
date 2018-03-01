package org.mydotey.samples.designpattern.visitor;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteVisitor2 implements Visitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("old teacher: " + teacher.getName());
    }

    @Override
    public void visit(Student student) {
        System.out.println("young student: " + student.getName());
    }

}
