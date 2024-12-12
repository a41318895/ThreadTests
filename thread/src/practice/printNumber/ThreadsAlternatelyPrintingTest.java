package practice.printNumber;

/**
 * Threads Alternately Printing Test
 *
 * <p>
 *     Print alternately odd numbers and even numbers within the range of 200 by two threads.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/12/12 Thu.
 */
public class ThreadsAlternatelyPrintingTest {

    public static void main(String[] args) {

        SupportResource resource = new SupportResource() ;

        Thread oddThread = new Thread(resource::printOdd, "OddThread") ;
        Thread evenThread = new Thread(resource::printEven, "EvenThread") ;

        // Increase thread priorities to reduce scheduling contention
        oddThread.setPriority(Thread.NORM_PRIORITY + 1) ;
        evenThread.setPriority(Thread.NORM_PRIORITY + 1) ;

        oddThread.setUncaughtExceptionHandler((thread, exception) ->
                System.err.println("Uncaught exception in " + thread.getName() + ": " + exception.getMessage())
        ) ;
        evenThread.setUncaughtExceptionHandler((thread, exception) ->
                System.err.println("Uncaught exception in " + thread.getName() + ": " + exception.getMessage())
        ) ;

        oddThread.start() ;
        evenThread.start() ;

        // Wait for threads to complete their tasks, then wake up the main thread
        resource.awaitCompletion() ;
        System.out.println("All threads completed") ;
    }
}
