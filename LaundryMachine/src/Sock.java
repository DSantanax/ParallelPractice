import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sock {

    private int maxSocks;
    private String color;
    private int producedSocks;
    private int matchedSocks;
    private static int totalSocks;
    private static int destroyedSocks;
    private BlockingQueue<String> bq;
    private AtomicBoolean done = new AtomicBoolean();

    public Sock(int maxSocks, String color, BlockingQueue<String> blockq) {
        this.maxSocks = maxSocks;
        this.color = color;
        producedSocks = 0;
        bq = blockq;
        totalSocks = totalSocks + maxSocks;
        destroyedSocks = 0;
        matchedSocks = 0;
    }

    public boolean isDone() {
        return done.get();
    }

    public synchronized String getSockColor() {
        return this.color;
    }

    public static synchronized void destroySocks() {
        destroyedSocks = destroyedSocks + 2;
    }

    public synchronized int getProducedSocks() {
        return this.producedSocks;
    }

    public synchronized int getMaxSocks() {
        return this.maxSocks;
    }

    public synchronized void createSocks() {
        // check if done
        if (producedSocks >= maxSocks) {
            done.getAndSet(true);
        }

        // check overflow production
        else if ((producedSocks + 1) > maxSocks) {
            producedSocks += (maxSocks - producedSocks);
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);
        } else {
            // else add 1
            producedSocks = producedSocks + 1;
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);
        }
    }

    public synchronized void matchingSocks() {
        // TODO: Find a way to cut matching to send signal to finish
        //TODO: fix prime number call
        try {
            if (matchedSocks < maxSocks) {
                //produced socks!= maxSocks or totalSocks?
                //produce cannot keep up with the matched
                if ((producedSocks % 2 == 0)) {
                    bq.put(color);
                    matchedSocks = matchedSocks +2;
                    System.out.format(
                            "Matching Thread: Send %s Socks to Washer. Total Socks %d. Total inside queue %d%n",
                            this.color, totalSocks, bq.size());
                }
            } else {
                Matching.setDone();
            }
        } catch (InterruptedException e) {
            System.err.println("Not done adding!");
        }
    }
}