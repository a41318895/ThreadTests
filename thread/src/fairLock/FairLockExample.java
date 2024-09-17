package fairLock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class FairLockExample {

    private final Lock fairLock = new ReentrantLock(true);
    private final Lock nonfairLock = new ReentrantLock() ;

    private static final Logger logger = Logger.getLogger(FairLockExample.class.getName());

    public void method1() {
        // In order to increase the rate of racing, increase the time fetch-lock & release-lock.
        for(int i = 0 ; i < 2 ; i ++) {
            fairLock.lock();

            try {
                logger.info(Thread.currentThread().getName() + " is holding the lock.");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.info(Thread.currentThread().getName() + " is interrupted.");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public void method2() {
        // In order to increase the rate of racing, increase the time fetch-lock & release-lock.
        for(int i = 0 ; i < 2 ; i ++) {
            nonfairLock.lock();

            try {
                logger.info(Thread.currentThread().getName() + " is holding the lock.");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.info(Thread.currentThread().getName() + " is interrupted.");
            } finally {
                nonfairLock.unlock();
            }
        }
    }

    /*
     * 公平鎖 ( Fair Lock ) 與 不公平鎖 ( UnFair Lock ) :
     *
     * Fair Lock :
     *      1. Thread will fetch lock according to the order of requesting, so every thread can fairly fetch the lock.
     *         In other word, it can avoid starvation occurring.
     *      2. It needs to maintain a queue which arrange the order of requesting, so it has poor-performance.
     *      3. It needs to switch the thread context frequently, so it has poor-performance.
     *      4. The result of showing thread may be like this : [ thread 1, 2, 3, 1, 2, 3 is holding lock ]
     *
     * UnFair Lock :
     *      1. Every thread will fetch lock randomly, it may occur starvation.
     *      2. No need to maintain a queue which arrange the order of requesting, so it has good-performance.
     *      3. No need to switch the thread context frequently, so it has good-performance.
     *      4. The result of showing thread may be like this : [ thread 2, 2, 1, 1, 3, 3 is holding lock ]
     */

    /*
     * 資源飢餓 ( Starvation ) :
     * Due to the frequent fetch-lock & release-lock behavior of highPriorityThread,
     * other threads may stuck in starvation which need to wait other thread for long time.
     */
    public static void main(String[] args) {

        FairLockExample example = new FairLockExample();
        ThreadMXBean threadMXBean1 = ManagementFactory.getThreadMXBean() ;


//        Runnable task1 = example::method1 ;
//
//        long startTime1 = System.nanoTime();
//        long startContextSwitch1 =
//                threadMXBean1.getThreadInfo(Thread.currentThread().getId()).getBlockedCount() ;
//
//        // In order to increase the rate of racing, increase the number of thread creating.
//        Thread[] threads1 = new Thread[3];
//        for(int i = 0 ; i < 3 ; i ++) {
//            threads1[i] = new Thread(task1, "Thread " + (i + 1)) ;
//            threads1[i].start();
//        }
//
//        for(Thread thread : threads1) {
//           try {
//               thread.join();
//           } catch (InterruptedException e) {
//               logger.info(Thread.currentThread().getName() + " is interrupted.") ;
//           }
//        }
//
//        long endTime1 = System.nanoTime() - startTime1 ;
//        long endContextSwitch1 =
//                threadMXBean1.getThreadInfo(Thread.currentThread().getId()).getBlockedCount() - startContextSwitch1 ;
//
//        logger.info("Time Spent Using Fair Lock : " + endTime1 + " nanoseconds.");
//        logger.info("Context Switch time Using Fair Lock : " + endContextSwitch1 + " times.");



        Runnable task2 = example::method2 ;

        long startTime2 = System.nanoTime() ;
        long startContextSwitch2 =
                threadMXBean1.getThreadInfo(Thread.currentThread().getId()).getBlockedCount() ;

        // In order to increase the rate of racing, increase the number of thread creating.
        Thread[] threads2 = new Thread[3];
        for(int i = 0 ; i < 3 ; i ++) {
            threads2[i] = new Thread(task2, "Thread " + (i+1)) ;
            threads2[i].start();
        }

        for(Thread thread : threads2) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.info(Thread.currentThread().getName() + " is interrupted.") ;
            }
        }

        long endTime2 = System.nanoTime() - startTime2 ;
        long endContextSwitch2 =
                threadMXBean1.getThreadInfo(Thread.currentThread().getId()).getBlockedCount() - startContextSwitch2 ;

        logger.info("Time Spent Not Using Fair Lock : " + endTime2 + " nanoseconds");
        logger.info("Context Switch time Not Using Fair Lock : " + endContextSwitch2 + " times.");
    }
}
