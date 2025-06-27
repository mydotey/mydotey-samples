package org.mydotey.samples.algorithm.loadbalance;

import java.util.List;
import java.util.function.Function;

import org.mydotey.java.ObjectExtension;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class HashLoadBalancer extends AbstractLoadBalancer {

    public static final Function<Object, Integer> DEFAULT_GET_HASH = obj -> obj == null ? 0 : obj.hashCode();

    private ThreadLocal<Object> _key = new ThreadLocal<>();
    private Function<Object, Integer> _getHash;

    public HashLoadBalancer() {
        this(DEFAULT_GET_HASH);
    }

    public HashLoadBalancer(Function<Object, Integer> getHash) {
        ObjectExtension.requireNonNull(getHash, "getHash");
        _getHash = getHash;
    }

    public void setKey(Object key) {
        _key.set(key);
    }

    protected Object getKey() {
        return _key.get();
    }

    public void clearKey() {
        _key.set(null);
    }

    protected int getHashCode(Object obj) {
        return _getHash.apply(obj);
    }

    @Override
    public Server chooseServer() {
        List<Server> servers = getServers();
        Object key = getKey();
        int hashCode = getHashCode(key);
        int index = hashCode % servers.size();
        return servers.get(index);
    }

}
