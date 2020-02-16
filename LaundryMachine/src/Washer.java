import java.util.concurrent.BlockingQueue;

public class Washer implements Runnable {
    // consumer
    private BlockingQueue<String> bq;

    public Washer(BlockingQueue<String> blockq) {
        bq = blockq;
    }

    @Override
    public void run() {
        try {
            for(String str = bq.take(); !str.equals("Done"); str = bq.take()){
            Thread.sleep(4500);
            System.out.format("Washer Thread: Destroyed %s socks Total Destroyed Pairs: %d%n", str);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done Washing");
    }

}
   