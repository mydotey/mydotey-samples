package org.mydotey.samples.designpattern.objectpool.autorefreshed;

import org.mydotey.samples.designpattern.objectpool.ObjectPoolEntry;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoRefreshedObjectPoolEntry extends ObjectPoolEntry {

    private long _creationTime;
    private boolean _closable;

    protected AutoRefreshedObjectPoolEntry(Integer index, Object obj) {
        super(index, obj);

        _creationTime = System.currentTimeMillis();
    }

    @Override
    protected Integer getIndex() {
        return super.getIndex();
    }

    @Override
    protected boolean isReleased() {
        return super.isReleased();
    }

    @Override
    protected void setReleased() {
        super.setReleased();
    }

    protected boolean isClosable() {
        return _closable;
    }

    protected void setClosable() {
        _closable = true;
    }

    protected long getCreationTime() {
        return _creationTime;
    }

}
