package org.mydotey.samples.designpattern.visitor.improved2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteTeacherVisitor implements TeacherVisitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("teacher: " + teacher.getName());
    }

}
