package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ReusableEntry implements Cloneable {

    private Integer _index;
    private volatile boolean _released;

    private Reusable _reusable;

    ReusableEntry(Integer index, Reusable reusable) {
        _index = index;
        _reusable = reusable;
    }

    Integer getIndex() {
        return _index;
    }

    boolean isReleased() {
        return _released;
    }

    void setReleased() {
        _released = true;
    }

    public Reusable getReusable() {
        return _reusable;
    }

    @Override
    protected ReusableEntry clone() {
        try {
            return (ReusableEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
