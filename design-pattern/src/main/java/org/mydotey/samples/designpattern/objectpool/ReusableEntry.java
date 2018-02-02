package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ReusableEntry {

    private Integer _index;
    private Reusable _reusable;

    ReusableEntry(Integer index, Reusable reusable) {
        _index = index;
        _reusable = reusable;
    }

    Integer getIndex() {
        return _index;
    }

    public Reusable getReusable() {
        return _reusable;
    }

}
