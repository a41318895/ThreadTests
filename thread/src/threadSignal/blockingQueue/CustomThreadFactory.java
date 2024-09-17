package threadSignal.blockingQueue;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {

        Thread thread = new Thread(runnable) ;
        thread.setName("Consumer1");    // Set custom name to thread

        return thread ;
    }
}
