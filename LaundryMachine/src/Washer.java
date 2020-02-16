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
            Thread.sleep(4000);
            for(String str = bq.take(); !str.equals("Done"); str = bq.take()){
            System.out.format("Washer Thread: Destroyed %s socks%n", str);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done Washing");
    }

}
   