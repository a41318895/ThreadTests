package threadSynchronization;

public class ICounterUseSynchronizedKeyword {

    private int counter = 0 ;

    public synchronized void increment() {
        counter ++ ;
    }

    public synchronized void decrement() {
        counter -- ;
    }

    public synchronized int getCounter() {
        return counter ;
    }
}
