import java.util.concurrent.BlockingQueue;

public class Matching implements Runnable {
    private BlockingQueue<String> bq;
    private Sock blue;
    private Sock red;
    private Sock orange;
    private Sock green;
    private volatile static boolean done;

    public Matching(BlockingQueue<String> bq, Sock blu, Sock rd, Sock org, Sock grn) {
        this.bq = bq;
        blue = blu;
        red = rd;
        orange = org;
        green = grn;
        done = false;
    }

    public static void setDone() {
        done = true;
    }

    @Override
    public void run() {
        // TODO: Fix Queue to match with total socks and inside queue max!

        // Thread washer immediately takes

        while (!done) {
            try {
                Thread.sleep(2500);
                blue.matchingSocks();
                red.matchingSocks();
                orange.matchingSocks();
                green.matchingSocks();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            bq.put("Done");
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}