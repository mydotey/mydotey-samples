package org.mydotey.samples.designpattern.visitor.improved;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteVisitor2 extends AbstractVisitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("old teacher: " + teacher.getName());
    }

    @Override
    public void visit(Student student) {
        System.out.println("young student: " + student.getName());
    }

}
