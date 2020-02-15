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
            Thread.sleep(5000);
            Sock.destroySocks();
            System.out.format("Washer Thread: Destroyed %s socks Total Destroyed: %d%n", str, Sock.destroyedSocks);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     
    }
}
   