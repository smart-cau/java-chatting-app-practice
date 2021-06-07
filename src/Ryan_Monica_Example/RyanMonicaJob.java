package Ryan_Monica_Example;

public class RyanMonicaJob implements Runnable{
    private BankAccount account = new BankAccount();

    public static void main(String[] args) {
        RyanMonicaJob theJob = new RyanMonicaJob();

        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);

        one.setName("Ryan");
        two.setName("Monica");

        one.start();
        two.start();
    }

    private void makeWithdrawal(int amount) {
        if(account.getBalance() >= amount) {
            System.out.println(Thread.currentThread().getName() + " is about to withdraw");
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " woke up");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName()+ " completes the withdrawal and the balance is ..." + account.getBalance());
        } else {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName());
        }
    }

    @Override
    public void run() {
        for(int x = 0; x < 10; x++) {
            makeWithdrawal(10);
            if(account.getBalance() < 0)
                System.out.println("Overdrawn!"); // 이 문구가 출력된다 -> 공통자원 관리가 두 스레디의 동시성으로 인해 관리가 안되었다
        }
    }
}
