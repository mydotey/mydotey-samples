package org.mydotey.samples.algorithm.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
public class ConsistentHashLoadBalancer extends HashLoadBalancer {

    private volatile HashLoop _hashLoop;
    private int _virtualNodeCount;

    public ConsistentHashLoadBalancer() {
        this(1);
    }

    public ConsistentHashLoadBalancer(int virtualNodeCount) {
        this(DEFAULT_GET_HASH, virtualNodeCount);
    }

    public ConsistentHashLoadBalancer(Function<Object, Integer> getHash, int virtualNodeCount) {
        super(getHash);

        if (virtualNodeCount < 1)
            throw new IllegalArgumentException("virtualNodeCount < 1");
        _virtualNodeCount = virtualNodeCount;
    }

    @Override
    public synchronized void setServers(List<Server> servers) {
        super.setServers(servers);

        HashMap<Integer, Server> hashCodeServerMap = constructHashCodeServerMap(servers);
        _hashLoop = new HashLoop(hashCodeServerMap);
    }

    protected HashMap<Integer, Server> constructHashCodeServerMap(List<Server> servers) {
        HashMap<Integer, Server> hashCodeServerMap = new HashMap<>();
        servers.forEach(s -> addServer(hashCodeServerMap, s));
        return hashCodeServerMap;
    }

    protected void addServer(HashMap<Integer, Server> hashCodeServerMap, Server server) {
        int hashCode = getHashCode(server);
        for (int i = 0, j = 0; i < _virtualNodeCount && j < 10000; j++) {
            if (!hashCodeServerMap.containsKey(hashCode)) {
                hashCodeServerMap.put(hashCode, server);
                i++;
                continue;
            }

            hashCode = getHashCode(hashCode + "virtual-node" + j);
        }
    }

    @Override
    public Server chooseServer() {
        if (_hashLoop == null)
            throw new IllegalStateException("servers is empty");

        Object key = getKey();
        int hashCode = getHashCode(key);
        return _hashLoop.getServer(hashCode);
    }

    protected class HashLoop {

        private HashMap<Integer, Server> _hashCodeServerMap;

        private List<Integer> _hashCodes;

        public HashLoop(HashMap<Integer, Server> hashCodeServerMap) {
            _hashCodeServerMap = hashCodeServerMap;
            _hashCodes = new ArrayList<>(_hashCodeServerMap.keySet());
            _hashCodes.sort((i, i2) -> i > i2 ? 1 : (i == i2 ? 0 : -1));
        }

        public Server getServer(int hashCode) {
            int index = search(hashCode);
            return _hashCodeServerMap.get(_hashCodes.get(index));
        }

        protected int search(int hashCode) {
            if (_hashCodes.get(_hashCodes.size() - 1) < hashCode)
                return 0;

            return search(hashCode, 0, _hashCodes.size() - 1);
        }

        protected int search(int hashCode, int start, int end) {
            if (end - start == 0)
                return end;

            int middle = (start + end) / 2;
            if (_hashCodes.get(middle) > hashCode)
                return search(hashCode, start, middle);

            return search(hashCode, middle + 1, end);
        }

    }

}
