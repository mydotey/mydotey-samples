package org.mydotey.samples.designpattern.memento2;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Mementoable {

    void createMemento();

    void rollback();

}
