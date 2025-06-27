package org.mydotey.samples.designpattern.memento;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface Mementoable {

    Memento createMemento();

    void restore(Memento memento);

}
