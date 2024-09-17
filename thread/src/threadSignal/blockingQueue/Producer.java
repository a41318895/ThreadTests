package threadSignal.blockingQueue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private final BlockingQueue<Integer> blockingQueue ;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        try {

            for(int i = 1 ; i <= 10 ; i ++) {
                blockingQueue.put(i);
                System.out.println("[ " + Thread.currentThread().getName() + " ] produced : " + i + " --- Queue Capacity : " + blockingQueue.size() + " / 5");

                Thread.sleep(1000);     // Mock produce time
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}

