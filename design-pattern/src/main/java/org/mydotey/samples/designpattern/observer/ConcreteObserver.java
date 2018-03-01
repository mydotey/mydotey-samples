package org.mydotey.samples.designpattern.observer;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteObserver implements Observer {

    private Observable _observable;

    public ConcreteObserver(Observable observable) {
        _observable = observable;
    }

    @Override
    public void observe() {
        System.out.println("observer " + hashCode() + " observed an event from observable " + _observable.hashCode());
    }

}
