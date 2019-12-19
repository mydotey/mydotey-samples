package org.mydotey.samples.algorithm.rwlock;

public class RwLockImpl2 implements RwLock {
    
    private volatile int readCount;
    private volatile boolean writing;

    @Override
    public synchronized void lockRead() throws InterruptedException {
        while (writing)
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
        while (writing)
            wait();

        writing = true;
        
        while (readCount > 0)
            wait();
    }

    @Override
    public synchronized void unlockWrite() {
        writing = false;
        notifyAll();
    }

}
