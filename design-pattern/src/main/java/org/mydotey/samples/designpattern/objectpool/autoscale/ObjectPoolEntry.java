package org.mydotey.samples.designpattern.objectpool.autoscale;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ObjectPoolEntry implements Cloneable {

    protected interface Status {
        String AVAILABLE = "available";
        String ACQUIRED = "acquired";
        String RELEASED = "released";
    }

    private Integer _number;
    private volatile String _status;

    private Object _obj;

    protected ObjectPoolEntry(Integer number, Object obj) {
        _number = number;
        _obj = obj;
    }

    protected Integer getNumber() {
        return _number;
    }

    protected String getStatus() {
        return _status;
    }

    protected void setStatus(String status) {
        _status = status;
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
