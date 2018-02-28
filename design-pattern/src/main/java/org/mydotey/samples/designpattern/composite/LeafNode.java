package org.mydotey.samples.designpattern.composite;

import java.util.Collection;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class LeafNode implements Node {

    @Override
    public void showMe() {
        System.out.println("I'm a leaf");
    }

    @Override
    public Collection<Node> getSubNodes() {
        return null;
    }

}
