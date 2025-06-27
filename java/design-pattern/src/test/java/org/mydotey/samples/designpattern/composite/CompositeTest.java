package org.mydotey.samples.designpattern.composite;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public class CompositeTest {

    @Test
    public void compositeTest() {
        LeafNode leaf = new LeafNode();
        RootNode root = new RootNode();
        root.getSubNodes().add(leaf);

        Node node = root;
        node.showMe();

        node = leaf;
        node.showMe();
    }

}
