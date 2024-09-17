package threadSignal.waitAndNotify;

// Store and manage the data and hasData logic flag
public class SharedResource {

    private int data ;
    private boolean hasData ;

    public synchronized void produce(int data) {

        // If hasData, wait for 'consumer thread' consuming the data.
        while(hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt() ;
            }
        }
        this.data = data ;
        hasData = true ;
        notifyAll();  // Notify 'consumer threads' to check the condition itself
    }

    public synchronized int consume() {

        // If notHasData, wait for 'producer thread' producing the data.
        while(!hasData) {
            try{
                wait() ;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt() ;
            }
        }
        hasData = false ;
        notifyAll() ;
        return data ;   // Notify 'producer threads' to check the condition itself
    }
}
