package org.mydotey.samples.designpattern.singleton;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class LazySingleton {

    private static volatile LazySingleton _instance; // use volatile to prevent jvm compiler instruction reordering

    public static LazySingleton getInstance() {
        if (_instance != null)
            return _instance;

        synchronized (LazySingleton.class) {
            if (_instance != null)
                return _instance;

            /* The assignment is not an atomic instruction.
             * Actually it will be divided into 3 atomic instructions:
             * 1. new instance
             * 2. init instance
             * 3. assign instance address to variable
             * Step 2/3 may be reordered, so other threads probably get a non null value before the instance init is completed.
             * Use volatile to prevent!
             */
            _instance = new LazySingleton();
            return _instance;
        }
    }

    private LazySingleton() {

    }

}
