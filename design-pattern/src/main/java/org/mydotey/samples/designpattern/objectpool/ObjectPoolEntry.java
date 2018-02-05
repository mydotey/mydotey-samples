package org.mydotey.samples.designpattern.objectpool;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPoolEntry implements Cloneable {

    private Integer _index;
    private volatile boolean _released;

    private Object _obj;

    protected ObjectPoolEntry(Integer index, Object obj) {
        _index = index;
        _obj = obj;
    }

    protected Integer getIndex() {
        return _index;
    }

    protected boolean isReleased() {
        return _released;
    }

    protected void setReleased() {
        _released = true;
    }

    public Object getObject() {
        return _obj;
    }

    @Override
    public ObjectPoolEntry clone() {
        try {
            return (ObjectPoolEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
