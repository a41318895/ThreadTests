package threadSignal.waitAndNotify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Producer implements Runnable{

    private final SharedResource sharedResource ;
    private final int[] produceData ;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss.SSS");

    public Producer(SharedResource sharedResource, int[] produceData) {
        this.sharedResource = sharedResource;
        this.produceData = produceData ;
    }

    @Override
    public void run() {

        for (int singleProduceData : produceData) {

            sharedResource.produce(singleProduceData);
            System.out.println("[ " + Thread.currentThread().getName() + " ] produced : " + singleProduceData + " ----- " + LocalDateTime.now().format(formatter));

            try {
                Thread.sleep(1000);   // Mock the produce time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
