package org.mydotey.samples.designpattern.composite;

import java.util.Collection;

/**
 * @author koqizhao
 *
 * Feb 28, 2018
 */
public interface Node {

    void showMe();

    Collection<Node> getSubNodes();

}
