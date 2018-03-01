package org.mydotey.samples.designpattern.visitor.improved2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteStudentVisitor2 implements StudentVisitor {

    @Override
    public void visit(Student student) {
        System.out.println("young student: " + student.getName());
    }

}
