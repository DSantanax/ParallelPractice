import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sock {

    private int maxSocks;
    private String color;
    private int producedSocks;
    private static int totalSocks;
    private BlockingQueue<String> producer;
    private BlockingQueue<String> dest;
    private int pairs;
    private static int count;
    private AtomicBoolean done = new AtomicBoolean();

    public Sock(int maxSocks, String color, BlockingQueue<String> blockq, BlockingQueue<String> dst) {
        this.maxSocks = maxSocks;
        this.color = color;
        producedSocks = 0;
        producer = blockq;
        count = 0;
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
        if (producedSocks >= maxSocks) {
            done.getAndSet(true);
        }
        // check overflow production
        else if ((producedSocks + 1) > maxSocks) {
            producedSocks += (maxSocks - producedSocks);

            if (producedSocks % 2 == 0)
                producer.add(color);
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);

        } else {
            // else add 1
            producedSocks = producedSocks + 1;
            if (producedSocks % 2 == 0)
                producer.add(color);
            System.out.format("%s Sock: Produced %d of %d %s Socks%n", color, producedSocks, maxSocks, color);

        }
    }

    public synchronized void matchingSocks() {
        // TODO: Fix matchingSocks loop until no more
        try {
            if ((pairs + 1) < maxSocks) {
                // produce cannot keep up with the matched
                String str = producer.take();
                pairs = pairs + 2;
                // remove pair from producer
                dest.put(str);
                System.out.format("Matching Thread: Send %s Socks to Washer. Total Socks %d. Total inside queue %d%n",
                        this.color, totalSocks, producer.size());
            } else {
                if (count == 4)
                    Matching.setDone();
                count++;
            }
        } catch (InterruptedException e) {
            System.err.println("Not done adding!");
        }
    }
}