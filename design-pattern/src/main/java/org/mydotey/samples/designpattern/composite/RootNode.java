package org.mydotey.samples.designpattern.composite;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class RootNode implements Node {

    private Collection<Node> _subNodes = new HashSet<>();

    @Override
    public void showMe() {
        System.out.println(String.format("I'm a root with %d sub nodes", _subNodes.size()));
    }

    @Override
    public Collection<Node> getSubNodes() {
        return _subNodes;
    }

}
