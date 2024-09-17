package threadSignal.waitAndNotify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consumer implements Runnable{

    private final SharedResource sharedResource;
    private final int consumeVolume ;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss.SSS");

    public Consumer(SharedResource sharedResource, int consumeVolume) {
        this.sharedResource = sharedResource;
        this.consumeVolume = consumeVolume;
    }

    @Override
    public void run() {

        for(int i = 0 ; i < consumeVolume ; i++) {

            int consumedData = sharedResource.consume();
            System.out.println("[ " + Thread.currentThread().getName() + " ] consumed : " + consumedData + " ----- " + LocalDateTime.now().format(formatter)) ;

            try{
                Thread.sleep(1500);  // Mock the consume time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
