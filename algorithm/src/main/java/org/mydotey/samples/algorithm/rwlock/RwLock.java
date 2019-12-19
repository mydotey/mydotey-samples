package org.mydotey.samples.algorithm.rwlock;

public interface RwLock {
    
    void lockRead() throws InterruptedException;
    
    void unlockRead();
    
    void lockWrite() throws InterruptedException;
    
    void unlockWrite();

}
