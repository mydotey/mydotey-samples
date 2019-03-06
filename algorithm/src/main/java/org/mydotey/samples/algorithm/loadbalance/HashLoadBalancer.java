package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class HashLoadBalancer<T> extends AbstractLoadBalancer {

    private ThreadLocal<T> _key = new ThreadLocal<>();

    public void setKey(T key) {
        _key.set(key);
    }

    public void clearKey() {
        _key.set(null);
    }

    protected int getHashCode() {
        T currentKey = _key.get();
        return currentKey == null ? 0 : currentKey.hashCode();
    }

    @Override
    public Server chooseServer() {
        List<Server> servers = getServers();
        int currentHashCode = getHashCode();
        int index = currentHashCode % servers.size();
        return servers.get(index);
    }

}
