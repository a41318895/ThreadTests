package threadSynchronization;

import java.util.logging.Logger;

public class ICounterSynchronizationTest {

    private static final Logger logger = Logger.getLogger(ICounterSynchronizationTest.class.getName()) ;

    public static void main(String[] args) {

        ICounterUseSynchronizedKeyword counter = new ICounterUseSynchronizedKeyword() ;

        Thread thread1 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) {
                counter.increment();
            }
        }) ;

        Thread thread2 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) {
                counter.increment();
            }
        }) ;

        thread1.start();
        thread2.start();

        try {
            thread1.join();     // main thread is waiting for thread1 finishing
            thread2.join();     // main thread is waiting for thread2 finishing
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        logger.info("Final Count : " + counter.getCounter());   // 2000 2000 2000 2000 2000...



        ICounterUseReentrantLock counter2 = new ICounterUseReentrantLock() ;

        Thread thread3 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) {
                counter2.increment();
            }
        }) ;

        Thread thread4 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) {
                counter2.increment();
            }
        }) ;

        thread3.start();
        thread4.start();

        try {
            thread3.join();     // main thread is waiting for thread1 finishing
            thread4.join();     // main thread is waiting for thread2 finishing
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        logger.info("Final Count : " + counter2.getCounter());   // 2000 2000 2000 2000 2000...
    }
}
