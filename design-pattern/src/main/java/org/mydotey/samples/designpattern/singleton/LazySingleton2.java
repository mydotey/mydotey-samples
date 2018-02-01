package org.mydotey.samples.designpattern.singleton;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class LazySingleton2 {

    private static class Holder {
        private static LazySingleton2 _instance = new LazySingleton2();
    }

    public static LazySingleton2 getInstance() {
        return Holder._instance;
    }

    private LazySingleton2() {

    }

}
