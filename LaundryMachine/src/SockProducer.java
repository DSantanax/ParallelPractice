public class SockProducer implements Runnable {
    private Sock sock;

    public SockProducer(Sock s) {
        this.sock = s;
    }

    @Override
    public void run() {
        while (!sock.isDone()) {
            try {
                Thread.sleep(1500);
                sock.createSocks();
            } catch (InterruptedException e) {

            }
        }
    }
}