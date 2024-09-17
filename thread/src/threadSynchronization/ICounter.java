package threadSynchronization;

public class ICounter {

    private int counter = 0 ;

    public void increment() {
        counter ++ ;
    }

    public void decrement() {
        counter -- ;
    }

    public int getCounter() {
        return counter ;
    }
}
