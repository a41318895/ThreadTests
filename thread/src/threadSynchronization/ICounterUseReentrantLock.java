package threadSynchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ICounterUseReentrantLock {

    private int counter = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {

        lock.lock();
        try {
            counter++ ;
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {

        lock.lock();
        try {
            counter-- ;
        } finally {
            lock.unlock();
        }
    }

    public int getCounter() {

        lock.lock();
        try {
            return counter ;
        } finally {
            lock.unlock();
        }
    }
}