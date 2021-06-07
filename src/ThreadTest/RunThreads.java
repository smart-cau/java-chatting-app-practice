package ThreadTest;

public class RunThreads implements Runnable{
    @Override
    public void run() {
        for(int i = 0; i < 50 ; i++) {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " is Running");
        }
    }

    public static void main(String[] args) {
        RunThreads runner = new RunThreads();
        Thread alpha = new Thread(runner);
        Thread beta = new Thread(runner);

        alpha.setName("Alpha thread");
        beta.setName("Beta thread");

        alpha.start();
        beta.start();
    }
}
