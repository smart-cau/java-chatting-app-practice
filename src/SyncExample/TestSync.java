package SyncExample;

public class TestSync implements Runnable{
    private int balance;
    @Override
    public void run() {
        for(int i = 0; i < 50; i++) {
            increment();
            System.out.println("The balance is " + balance + "--thread name is "+ Thread.currentThread().getName());
        }
    }
    private synchronized void increment() { // synchronized keyword를 붙여줌으로써, 더이상 쪼갤 수 없게 atomic하게 만들어줌
        int i = balance;
        balance = i + 1;
    }

    public static void main(String[] args) {
        TestSync theJob = new TestSync();

        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);

        one.setName("A");
        two.setName("B");

        one.start();
        two.start();
    }
}
