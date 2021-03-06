package org.mydotey.samples.designpattern.visitor;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Visitor {

    void visit(Teacher teacher);

    void visit(Student student);

}
