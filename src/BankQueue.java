import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankQueue {
    private final Queue<Customer> queue;
    private final int maxQueueLength;
    private final Lock lock = new ReentrantLock();
    private final Teller[] tellers;

    public BankQueue(int numTellers, int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
        this.queue = new LinkedList<>();
        this.tellers = new Teller[numTellers];
        for (int i = 0; i < numTellers; i++) {
            tellers[i] = new Teller(this);
            new Thread(tellers[i]).start();
        }
    }

    public boolean addCustomer(Customer customer) {
        lock.lock();
        try {
            if (queue.size() >= maxQueueLength) {
                return false;
            }
            customer.setQueueEntryTime(System.currentTimeMillis());
            queue.offer(customer);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Customer getNextCustomer() {
        lock.lock();
        try {
            Customer customer = queue.peek();
            if (customer != null && (System.currentTimeMillis() - customer.getQueueEntryTime() > 10)) {
                queue.poll();
                customer.setLeft(true);
                return null;
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }
}

class Teller implements Runnable {
    private final BankQueue bankQueue;

    public Teller(BankQueue bankQueue) {
        this.bankQueue = bankQueue;
    }

    @Override
    public void run() {
        while (true) {
            Customer customer = bankQueue.getNextCustomer();
            if (customer != null) {
                if (customer.hasLeft()) {
                    continue;
                }
                try {
                    Thread.sleep(customer.getServiceTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customer.setServed(true);
            } else {
                try {
                    Thread.sleep(1); // Wait for 1 millisecond before checking again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
