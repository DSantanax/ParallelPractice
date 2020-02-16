import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SockMatching {

    private static String[] colors = { "Blue", "Red", "Orange", "Green" };

    public static void main(String[] args) {
        Random rn = new Random();
        int frstRand = rn.nextInt(100);
        int scdRand = rn.nextInt(100);
        int thrdRand = rn.nextInt(100);
        int fthRand = rn.nextInt(100);
        int sum = frstRand + scdRand + thrdRand + fthRand;
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(sum);
        BlockingQueue<String> destroyer = new ArrayBlockingQueue<>(sum);

        Sock bl = new Sock(rn.nextInt(frstRand) + 1, colors[0], bq, destroyer);
        Sock rd = new Sock(rn.nextInt(scdRand) + 1, colors[1], bq, destroyer);
        Sock org = new Sock(rn.nextInt(thrdRand)+ 1, colors[2], bq, destroyer);
        Sock grn = new Sock(rn.nextInt(fthRand) + 1, colors[3], bq, destroyer);

        Thread blue = new Thread(new SockProducer(bl));
        blue.start();

        Thread red = new Thread(new SockProducer(rd));
        red.start();

        Thread orange = new Thread(new SockProducer(org));
        orange.start();

        Thread green = new Thread(new SockProducer(grn));
        green.start();

        Thread matching = new Thread(new Matching(destroyer, bl, rd, org, grn));
        matching.start();

        Thread washer = new Thread(new Washer(destroyer));
        washer.start();

    }
}