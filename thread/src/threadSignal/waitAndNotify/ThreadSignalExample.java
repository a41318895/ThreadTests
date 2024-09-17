package threadSignal.waitAndNotify;

public class ThreadSignalExample {

    public static void main(String[] args) {

        SharedResource sharedResource = new SharedResource();
        int[] producingDataArray = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        Thread producerThread1 = new Thread(new Producer(sharedResource, producingDataArray), "ProducerThread1") ;
        Thread producerThread2 = new Thread(new Producer(sharedResource, producingDataArray), "ProducerThread2") ;
        Thread consumerThread1 = new Thread(new Consumer(sharedResource, producingDataArray.length), "ConsumerThread1") ;
        Thread consumerThread2 = new Thread(new Consumer(sharedResource, producingDataArray.length), "ConsumerThread2") ;

        producerThread1.start();
        producerThread2.start();
        consumerThread1.start();
        consumerThread2.start();
    }
}
