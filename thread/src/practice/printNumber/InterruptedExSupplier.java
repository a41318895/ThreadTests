package practice.printNumber;

@FunctionalInterface
public interface InterruptedExSupplier {

    void run() throws InterruptedException ;
}
