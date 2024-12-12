package practice.printNumber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SupportResource {

    // Reentrant lock with fairness enabled to prevent thread starvation
    private static final ReentrantLock lock = new ReentrantLock(true) ;

    // Condition variables for odd and even number synchronization
    private static final Condition conditionOdd = lock.newCondition() ;
    private static final Condition conditionEven = lock.newCondition() ;

    // Atomic integer for thread-safe counting
    private static final AtomicInteger numberToCount = new AtomicInteger(0) ;

    // Maximum number to be printed by threads
    private static final Integer MAX_NUMBER = 200 ;

    // CountDownLatch to ensure main thread waits for indicated numbers of thread to complete their tasks
    private static final CountDownLatch completionLatch = new CountDownLatch(2) ;

    public void printOdd() {

        executeWithLatch(() -> {

            while (isStillInRange()) {

                lock.lock() ;
                try {

                    execute(() -> {

                        while (isStillInRange() && isNumberCountEven()) {

                            // If not odd number, await signal from Even Thread
                            safeAwait(conditionOdd) ;
                        }
                    }) ;

                    if (!isStillInRange()) return ;

                    // Print odd number
                    System.out.println(Thread.currentThread().getName() + " : " + numberToCount.getAndIncrement()) ;

                    // Signal waiting Even Thread
                    conditionEven.signal() ;

                } finally {

                    lock.unlock() ;
                }
            }
        }) ;
    }

    public void printEven() {

        executeWithLatch(() -> {

            while (isStillInRange()) {

                lock.lock() ;
                try {

                    execute(() -> {

                        // If not even number, await signal from Odd Thread
                        while (isStillInRange() && isNumberCountOdd()) {

                            safeAwait(conditionEven) ;
                        }
                    }) ;

                    if (!isStillInRange()) return ;

                    // Print even number
                    System.out.println(Thread.currentThread().getName() + " : " + numberToCount.getAndIncrement()) ;

                    // Signal waiting Odd Thread
                    conditionOdd.signal() ;

                } finally {

                    lock.unlock() ;
                }
            }
        }) ;
    }

    private static void executeWithLatch(InterruptedExSupplier supplier) {

        try {

            supplier.run() ;
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt() ;
            System.err.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage()) ;
        } finally {
            // Decrease the latch count
            completionLatch.countDown() ;
        }
    }

    private static void safeAwait(Condition condition) {

        execute(condition::await) ;
    }

    private static boolean isStillInRange() {

        return numberToCount.get() <= MAX_NUMBER ;
    }

    private static boolean isNumberCountOdd() {

        return numberToCount.get() % 2 == 1 ;
    }

    private static boolean isNumberCountEven() {

        return numberToCount.get() % 2 == 0 ;
    }

    public void awaitCompletion()  {

        execute(completionLatch::await) ;
    }

    // Generic method to execute InterruptedExSupplier with exception handling
    private static void execute(InterruptedExSupplier interruptedExSupplier) {

        try {

            interruptedExSupplier.run() ;
        } catch (InterruptedException e) {

            System.out.println(e.getMessage()) ;
        }
    }
}
