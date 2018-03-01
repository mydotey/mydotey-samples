package org.mydotey.samples.designpattern.visitor.improved;

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

        Visitor visitor1 = new ConcreteVisitor();
        for (Person person : school.getPersons()) {
            visitor1.visit(person);
        }

        System.out.println();

        Visitor visitor2 = new ConcreteVisitor2();
        for (Person person : school.getPersons()) {
            visitor2.visit(person);
        }
    }

}
