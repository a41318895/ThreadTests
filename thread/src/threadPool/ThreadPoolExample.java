package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

    public static void main(String[] args) {

        // Create a thread pool which contains 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5) ;

        // Thread pool will repeatedly call these 5 threads to finish 10 tasks.
        for(int i = 1 ; i <= 10 ; i ++) {

            final int taskId = i ;

            executor.execute(() ->
                    System.out.println("Task [ " + taskId + " ] is running on Thread [ " + Thread.currentThread().getName() + " ]" ));
        }

        executor.shutdown() ;
    }
}
