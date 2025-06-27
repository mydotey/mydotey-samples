package org.mydotey.samples.algorithm.loadbalance;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class ConcreteHashServer extends ConcreteServer {

    private int _hashCode;

    public ConcreteHashServer(int hashCode) {
        if (hashCode < 0)
            throw new IllegalArgumentException("hashCode < 0");

        _hashCode = hashCode;
    }

    @Override
    public int hashCode() {
        return _hashCode;
    }

}
