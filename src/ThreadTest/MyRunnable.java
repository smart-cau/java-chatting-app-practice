package ThreadTest;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("--run-- \n");
        go();
    }
    public void go(){
        // thread 작업 순서를 보장하기 위한 sleep() 활용

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("--go-- \n");
        doMore();
    }
    public void doMore(){
        System.out.println("--doMore-- \n");
    }
}
