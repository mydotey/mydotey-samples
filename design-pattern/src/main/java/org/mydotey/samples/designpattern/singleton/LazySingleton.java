package org.mydotey.samples.designpattern.singleton;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class LazySingleton {

    private static LazySingleton _instance;

    public static LazySingleton getInstance() {
        if (_instance != null)
            return _instance;

        synchronized (LazySingleton.class) {
            if (_instance != null)
                return _instance;

            _instance = new LazySingleton();
            return _instance;
        }
    }

    private LazySingleton() {

    }

}
