import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sock {

    private int maxSocks;
    private String color;
    private int producedSocks;
    private static int totalSocks;
    private BlockingQueue<String> producer;
    private BlockingQueue<String> dest;
    private AtomicBoolean done = new AtomicBoolean();

    public Sock(int maxSocks, String color, BlockingQueue<String> blockq, BlockingQueue<String> dst) {
        this.maxSocks = maxSocks;
        this.color = color;
        producedSocks = 0;
        producer = blockq;
        totalSocks = totalSocks + maxSocks;
        dest = dst;
    }

    public boolean isDone() {
        return done.get();
    }

    public synchronized String getSockColor() {
        return this.color;
    }

    public synchronized int getProducedSocks() {
        return this.producedSocks;
    }

    public synchronized int getMaxSocks() {
        return this.maxSocks;
    }

    public synchronized void createSocks() {
        // check if done
        if (producedSocks == maxSocks) {
            done.getAndSet(true);
        }
        // check overflow production
        else if ((producedSocks + 1) > maxSocks) {
            producedSocks += (maxSocks - producedSocks);
            producer.add(color);
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);

        } else {
            // else add 1
            producedSocks = producedSocks + 1;
            producer.add(color);
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);
        }
    }

    public synchronized void matchingSocks() {
        // TODO: Fix matchingSocks
        HashSet<String> hsm = new HashSet<>();
        try {
            if (!producer.isEmpty()) {
                // produce cannot keep up with the matched
                for (String str : producer) {
                    if (hsm.contains(str)) {
                        //remove pair
                        hsm.remove(str);
                        String clr = producer.take();
                        dest.put(clr);
                        System.out.format(
                                "Matching Thread: Send %s Socks to Washer. Total Socks %d. Total inside queue %d%n",
                                this.color, totalSocks, producer.size());
                    } else {
                        hsm.add(color);
                    }
                }
            } else {
                Matching.setDone();
            }
        } catch (InterruptedException e) {
            System.err.println("Not done adding!");
        }
    }
}