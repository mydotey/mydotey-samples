package org.mydotey.samples.designpattern.visitor.improved;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteVisitor extends AbstractVisitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("teacher: " + teacher.getName());
    }

    @Override
    public void visit(Student student) {
        System.out.println("student: " + student.getName());
    }

}
