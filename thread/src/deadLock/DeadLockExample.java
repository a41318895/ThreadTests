package deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class DeadLockExample {

    private final Lock lock1 = new ReentrantLock() ;
    private final Lock lock2 = new ReentrantLock() ;

    private final Logger logger = Logger.getLogger(this.getClass().getName()) ;

    public void method1() {

        lock1.lock();
        try {
            logger.info("Holding lock 1...");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }

            lock2.lock();       // Waiting for lock 2 releasing to access
            try {
                logger.info("Holding lock 1 & lock 2...");
            } finally {
                lock2.unlock();
            }
        } finally {
            lock1.unlock();
        }
    }

    public void method2() {

        lock2.lock();
        try {
            logger.info("Holding lock 2...");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }

            lock1.lock();       // Waiting for lock 1 releasing to access
            try {
                logger.info("Holding lock 2 & lock 1...");
            } finally {
                lock1.unlock();
            }
        } finally {
            lock2.unlock();
        }
    }

    /*
     * 死鎖 (DeadLock) :
     * In this case, t1 fetched lock1, executing the logical codes, then waiting for accessing and fetching lock2.
     * t2 also fetch lock2, executing the logical codes, then waiting for accessing and fetching lock1.
     * For the reason that thread1 is waiting for the release of lock2, and thread2 is also waiting for lock1,
     * the 'DeadLock' occurs (Two threads are waiting for each other).
     */
    public static void main(String[] args) {

        DeadLockExample example = new DeadLockExample() ;

        Thread t1 = new Thread(example::method1) ;
        Thread t2 = new Thread(example::method2) ;

        t1.start();
        t2.start();
    }
}
