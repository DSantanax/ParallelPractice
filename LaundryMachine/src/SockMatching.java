import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SockMatching {

    private static String[] colors = { "Blue", "Red", "Orange", "Green" };

    public static void main(String[] args) {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(500);
        int rand = 10;
        Random rn = new Random();

        Sock bl = new Sock(rn.nextInt(rand) + 1, colors[0], bq);
        Sock rd = new Sock(rn.nextInt(rand) + 1, colors[1], bq);
        Sock org = new Sock(rn.nextInt(rand) + 1, colors[2], bq);
        Sock grn = new Sock(rn.nextInt(rand) + 1, colors[3], bq);

        Thread blue = new Thread(new SockProducer(bl));
        blue.start();

        Thread red = new Thread(new SockProducer(rd));
        red.start();

        Thread orange = new Thread(new SockProducer(org));
        orange.start();

        Thread green = new Thread(new SockProducer(grn));
        green.start();

        Thread matching = new Thread(new Matching(bq, bl, rd, org, grn));
        matching.start();
        
        Thread washer = new Thread(new Washer(bq));
        washer.start();

    }
}