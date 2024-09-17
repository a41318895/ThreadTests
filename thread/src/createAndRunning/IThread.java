package createAndRunning;

import java.util.logging.Logger;

public class IThread extends Thread {

    Logger logger = Logger.getLogger("IThread");

    public IThread() {
        logger.info("Thread was created") ;
    }

    @Override
    public void run() {
        logger.info("Thread is running") ;
    }
}
