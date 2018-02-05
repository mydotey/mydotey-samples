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

    Integer index() {
        return _index;
    }

    boolean released() {
        return _released;
    }
    
    void markReleased() {
        _released = true;
    }

    public Reusable reusable() {
        return _reusable;
    }

    @Override
    protected ReusableEntry clone() {
        try {
            return (ReusableEntry) super.clone();
        } catch (Exception e) {
            return new ReusableEntry(_index, _reusable);
        }
    }

}
