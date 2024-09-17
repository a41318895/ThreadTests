package createAndRunning;

public class FunctionTest {

    public static void main(String[] args) {

        IThread iThread = new IThread() ;
        iThread.start() ;


        Thread thread = new Thread(new IRunnable()) ;
        thread.start();

    }
}
