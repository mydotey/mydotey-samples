package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPoolEntry implements Cloneable {

    private Integer _index;
    private volatile boolean _released;

    private Object _reusable;

    ObjectPoolEntry(Integer index, Object reusable) {
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

    public Object getReusable() {
        return _reusable;
    }

    @Override
    protected ObjectPoolEntry clone() {
        try {
            return (ObjectPoolEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
