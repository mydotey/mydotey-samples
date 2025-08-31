package org.mydotey.samples.designpattern.visitor.improved2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteTeacherVisitor2 implements TeacherVisitor {

    @Override
    public void visit(Teacher teacher) {
        System.out.println("old teacher: " + teacher.getName());
    }

}
