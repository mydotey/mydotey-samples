package org.mydotey.samples.designpattern.singleton;

import org.mydotey.samples.designpattern.SingletonSupplier;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class LazySingleton3 {

    private static SingletonSupplier<LazySingleton3> _lazySupplier = new SingletonSupplier<>(() -> new LazySingleton3());

    public static LazySingleton3 getInstance() {
        return _lazySupplier.get();
    }

    private LazySingleton3() {

    }

}
