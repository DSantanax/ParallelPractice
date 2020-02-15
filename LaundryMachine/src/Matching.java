import java.util.concurrent.BlockingQueue;

public class Matching implements Runnable {
    BlockingQueue<String> bq;
    private Sock blue;
    private Sock red;
    private Sock orange;
    private Sock green;

    public Matching(BlockingQueue<String> bq, Sock blu, Sock rd, Sock org, Sock grn) {
        this.bq = bq;
        blue = blu;
        red = rd;
        orange = org;
        green = grn;
    }

    @Override
    public void run() {
        // TODO: Fix Queue to match with total socks and inside queue max!

        // Sock.destroyedSocks < Sock.allSocks
        //use bq?
        
        while(true){
            try {
                Thread.sleep(3000);
                
                blue.matchingSocks();
                red.matchingSocks();
                orange.matchingSocks();
                green.matchingSocks();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
}