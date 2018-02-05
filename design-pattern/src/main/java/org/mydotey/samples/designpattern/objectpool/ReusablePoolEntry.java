package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
@SuppressWarnings("unchecked")
public class ReusablePoolEntry<T extends Reusable<T>> implements Cloneable {

    private Integer _index;
    private volatile boolean _released;

    private Reusable<T> _reusable;

    ReusablePoolEntry(Integer index, Reusable<T> reusable) {
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

    public T getReusable() {
        return (T) _reusable;
    }

    @Override
    protected ReusablePoolEntry<T> clone() {
        try {
            return (ReusablePoolEntry<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
