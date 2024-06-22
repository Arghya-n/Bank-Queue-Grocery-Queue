


public class Main {
    private static int sharedVariable = 0;
    private static final int NUM_THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 1000;

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];
        for(int i=0;i<NUM_THREADS;i++){
            threads[i]=new Thread(new BankQueue());
            threads[i].start();
        }
    }

    public static class BankQueue implements Runnable{
        public void run(){

        }
    }
}
