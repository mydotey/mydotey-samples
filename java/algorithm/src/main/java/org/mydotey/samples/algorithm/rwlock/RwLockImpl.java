package org.mydotey.samples.algorithm.rwlock;

public class RwLockImpl implements RwLock {
    
    private volatile int readCount;
    private volatile int writeCount;

    @Override
    public synchronized void lockRead() throws InterruptedException {
        while (writeCount > 0)
            wait();
        
        readCount++;
    }

    @Override
    public synchronized void unlockRead() {
        readCount--;
        notifyAll();
    }

    @Override
    public synchronized void lockWrite() throws InterruptedException {
        while (writeCount > 0)
            wait();

        writeCount++;
        
        while (readCount > 0)
            wait();
    }

    @Override
    public synchronized void unlockWrite() {
        writeCount--;
        notifyAll();
    }

}
