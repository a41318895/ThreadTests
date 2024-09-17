package threadSignal.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class Consumer implements Runnable{

    private final BlockingQueue<Integer> blockingQueue ;
    private final ScheduledExecutorService executorService ;

    public Consumer(BlockingQueue<Integer> blockingQueue, ScheduledExecutorService executorService) {
        this.blockingQueue = blockingQueue;
        this.executorService = executorService;
    }

    @Override
    public void run() {

        try {

            Integer takenElement = blockingQueue.take();
            System.out.println("[ " + Thread.currentThread().getName() + " ] consumed : " + takenElement + " --- Queue Capacity : " + blockingQueue.size() + " / 5") ;

            if(blockingQueue.isEmpty()) {
                System.out.println("Queue is empty. Shutting down the executor service...");
                executorService.shutdown() ;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer interrupted: " + e.getMessage());
        }
    }
}
