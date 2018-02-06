package org.mydotey.samples.designpattern.objectpool.autoscale.autorefreshed;

import org.mydotey.samples.designpattern.objectpool.autoscale.ObjectPoolEntry;

/**
 * @author koqizhao
 *
 * Feb 5, 2018
 */
public class AutoScaleObjectPoolEntry extends ObjectPoolEntry {

    protected interface Status extends ObjectPoolEntry.Status {
        String PENDING_CLOSE = "pending_close";
        String CLOSED = "closed";
    }

    private long _creationTime;
    private volatile long _lastUsedTime;

    protected AutoScaleObjectPoolEntry(Integer index, Object obj) {
        super(index, obj);

        _creationTime = System.currentTimeMillis();
        _lastUsedTime = _creationTime;
    }

    @Override
    protected Integer getNumber() {
        return super.getNumber();
    }

    @Override
    protected String getStatus() {
        return super.getStatus();
    }

    @Override
    protected void setStatus(String status) {
        super.setStatus(status);
    }

    protected long getCreationTime() {
        return _creationTime;
    }

    protected long getLastUsedTime() {
        return _lastUsedTime;
    }

    protected void renew() {
        _lastUsedTime = System.currentTimeMillis();
    }

}
