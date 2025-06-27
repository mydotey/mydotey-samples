package org.mydotey.samples.algorithm.rwlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RwLockImpl3 implements RwLock {
    
    private ReentrantLock lock;
    private Condition readCondition;
    private Condition writeCondition;
    private Condition writeCondition2;
    
    private volatile int readCount;
    private volatile boolean writing;
    
    public RwLockImpl3() {
        lock = new ReentrantLock();
        readCondition = lock.newCondition();
        writeCondition = lock.newCondition();
        writeCondition2 = lock.newCondition();
    }

    @Override
    public void lockRead() throws InterruptedException {
        lock.lock();
        try {
            while (writing)
                readCondition.await();
            
            readCount++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void unlockRead() {
        lock.lock();
        try {
            readCount--;
            writeCondition2.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void lockWrite() throws InterruptedException {
        lock.lock();
        try {
            while (writing)
                writeCondition.await();

            writing = true;
            
            while (readCount > 0)
                writeCondition2.await();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void unlockWrite() {
        lock.lock();
        try {
            writing = false;
            if (lock.hasWaiters(writeCondition))
                writeCondition.signal();
            else
                readCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
