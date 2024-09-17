package createAndRunning;

import java.util.logging.Logger;

public class IRunnable implements Runnable {

    Logger logger = Logger.getLogger("IThreadImplementRunnable") ;

    @Override
    public void run() {
        logger.info("Thread [ " + this.getClass().getName() + " ] is running");
    }
}
