package org.mydotey.samples.designpattern.visitor.improved2;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class VisitorTest {

    @Test
    public void visitorTest() {
        Student student = new Student("s1");
        Teacher teacher = new Teacher("t1");
        School school = new School(Sets.newHashSet(student, teacher));

        TeacherVisitor visitor1 = new ConcreteTeacherVisitor();
        StudentVisitor visitor2 = new ConcreteStudentVisitor();
        Visitor visitor = new ConcreteVisitor(visitor1, visitor2);
        for (Person person : school.getPersons()) {
            visitor.visit(person);
        }

        System.out.println();

        visitor1 = new ConcreteTeacherVisitor2();
        visitor2 = new ConcreteStudentVisitor2();
        visitor = new ConcreteVisitor(visitor1, visitor2);
        for (Person person : school.getPersons()) {
            visitor.visit(person);
        }
    }

}
