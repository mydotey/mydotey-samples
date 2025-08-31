package org.mydotey.samples.designpattern.visitor.improved;

import java.lang.reflect.Method;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public abstract class AbstractVisitor implements Visitor {

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

}
