package org.mydotey.samples.designpattern.observer;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ObserverTest {

    @Test
    public void observerTest() {
        ConcreteObservable observable = new ConcreteObservable();
        observable.addObserver(new ConcreteObserver(observable));

        observable.doSomeThing();
    }

}
