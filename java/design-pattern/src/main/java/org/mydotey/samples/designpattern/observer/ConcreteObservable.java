package org.mydotey.samples.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteObservable implements Observable {

    private List<Observer> _observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        _observers.add(observer);
    }

    @Override
    public void doSomeThing() {
        notifyObserver();
        System.out.println("doing something");
    }

    private void notifyObserver() {
        for (Observer observer : _observers)
            observer.observe();
    }

}
