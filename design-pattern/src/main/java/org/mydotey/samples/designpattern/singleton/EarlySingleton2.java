package org.mydotey.samples.designpattern.singleton;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public enum EarlySingleton2 {

    // enum can prevent deserialization from creating new instance.
    INSTANCE;

    private EarlySingleton2() {

    }

}
