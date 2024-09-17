package threadSignal.blockingQueue;

import java.util.concurrent.*;

public class BlockingQueueExample {

    public static void main(String[] args) {

        // Thread-safe queue. It offers blocking put() and take() method.
        BlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(5);

        // Create thread to do Producer task
        Thread producerThread = new Thread(new Producer(linkedBlockingQueue), "Producer1");
        producerThread.start();

        // Create ScheduledExecutorService to schedule the task(Consumer) execution.
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, new CustomThreadFactory());
        executorService.scheduleAtFixedRate(new Consumer(linkedBlockingQueue, executorService), 3, 3, TimeUnit.SECONDS);

        try {

            producerThread.join();

            // After producing, we give threadPool 15 seconds to finish its task(Consumer).
            // If task was not finished within 15s (might be interrupted), we call shutdownNow() here.
            if(!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                executorService.shutdownNow() ;
                System.out.println("Executor Service did not terminate within 15 seconds");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}
