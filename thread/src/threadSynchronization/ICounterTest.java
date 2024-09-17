package threadSynchronization;

import java.util.logging.Logger;

public class ICounterTest {

    private static final Logger logger = Logger.getLogger("threadSynchronization") ;

    public static void main(String[] args) {

        ICounter counter = new ICounter() ;

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

        /*
         *  Race Condition (競爭條件) :
         *  It occurs when multiple threads simultaneously access or modify the shared resource,
         *  leading to unpredictable and inconsistent result depends on the order in which the thread was executed.
         */
        logger.info("Final Count : " + counter.getCounter());  // 2000 1816 2000 2000 1426...
    }
}
