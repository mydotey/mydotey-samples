package org.mydotey.samples.designpattern.singleton;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class EarlySingleton {

    private static EarlySingleton _instance = new EarlySingleton();

    public static EarlySingleton getInstance() {
        return _instance;
    }

    private EarlySingleton() {

    }

}
