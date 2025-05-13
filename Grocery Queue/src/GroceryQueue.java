import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroceryQueue {
    private final Queue<Customer> queue;
    private final int maxQueueLength;
    private final Lock lock = new ReentrantLock();

    public GroceryQueue(int maxQueueLength) {
        this.queue = new LinkedList<>();
        this.maxQueueLength = maxQueueLength;
    }

    public boolean addCustomer(Customer customer) {
        lock.lock();
        try {
            if (queue.size() >= maxQueueLength) {
                return false;
            }
            customer.setQueueEntryTime(System.currentTimeMillis());
            queue.offer(customer);
            customer.setInQueue(true);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Customer serveCustomer() {
        lock.lock();
        try {
            Customer customer = queue.peek();
            if (customer != null && (System.currentTimeMillis() - customer.getQueueEntryTime() > 10)) {
                queue.poll(); // Remove the customer from the queue
                customer.setLeft(true); // Mark the customer as having left
                return null; // Return null to indicate this customer should not be served
            }

            return queue.poll(); // Retrieve and remove the first customer for serving
        } finally {
            lock.unlock();
        }
    }

    public int getQueueLength() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
