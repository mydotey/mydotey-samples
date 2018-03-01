package org.mydotey.samples.designpattern.visitor.improved2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Visitor {

    void visit(Person person);

    void visit(Teacher teacher);

    void visit(Student student);

}
