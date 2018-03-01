package org.mydotey.samples.designpattern.visitor.improved2;

import java.lang.reflect.Method;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteVisitor implements Visitor {

    private TeacherVisitor _teacherVisitor;
    private StudentVisitor _studentVisitor;

    public ConcreteVisitor(TeacherVisitor teacherVisitor, StudentVisitor studentVisitor) {
        _teacherVisitor = teacherVisitor;
        _studentVisitor = studentVisitor;
    }

    @Override
    public void visit(Person person) {
        Class<?> clazz = person.getClass();
        try {
            Method method = this.getClass().getMethod("visit", clazz);
            method.invoke(this, person);
        } catch (Exception e) {
            System.out.println("Unsupported class: " + clazz);
        }
    }

    @Override
    public void visit(Teacher teacher) {
        _teacherVisitor.visit(teacher);
    }

    @Override
    public void visit(Student student) {
        _studentVisitor.visit(student);
    }

}
