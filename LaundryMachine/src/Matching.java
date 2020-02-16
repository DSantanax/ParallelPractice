import java.util.concurrent.BlockingQueue;

public class Matching implements Runnable {
    private BlockingQueue<String> dest;
    private Sock blue;
    private Sock red;
    private Sock orange;
    private Sock green;
    private volatile static boolean done;

    public Matching(BlockingQueue<String> bq, Sock blu, Sock rd, Sock org, Sock grn) {
        this.dest = bq;
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
        while (!done) {
            try {
                Thread.sleep(3500);
                
                blue.matchingSocks();

                red.matchingSocks();

                orange.matchingSocks();

                green.matchingSocks();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            dest.put("Done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}