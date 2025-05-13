import java.util.concurrent.ThreadLocalRandom;

public class GroceryQueues {
    private final GroceryQueue[] queues;
    private final Cashier[] cashiers;
    private volatile boolean running;

    public GroceryQueues(int numberOfQueues, int maxQueueLength) {
        this.queues = new GroceryQueue[numberOfQueues];
        this.cashiers = new Cashier[numberOfQueues];
        this.running = true;
        for (int i = 0; i < numberOfQueues; i++) {
            queues[i] = new GroceryQueue(maxQueueLength);
            cashiers[i] = new Cashier(queues[i], this);
            new Thread(cashiers[i]).start();
        }
    }

    public boolean addCustomer(Customer customer) {
        GroceryQueue minQueue = queues[0];
        for (GroceryQueue queue : queues) {
            if (queue.getQueueLength() < minQueue.getQueueLength()) {
                minQueue = queue;
            } else if (queue.getQueueLength() == minQueue.getQueueLength() && ThreadLocalRandom.current().nextBoolean()) {
                minQueue = queue;
            }
        }
        return minQueue.addCustomer(customer);
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}

class Cashier implements Runnable {
    private final GroceryQueue groceryQueue;
    private final GroceryQueues groceryQueues;

    public Cashier(GroceryQueue groceryQueue, GroceryQueues groceryQueues) {
        this.groceryQueue = groceryQueue;
        this.groceryQueues = groceryQueues;
    }

    @Override
    public void run() {
        while (groceryQueues.isRunning()) {
            Customer customer = groceryQueue.serveCustomer();
            if (customer != null) {
                if (customer.hasLeft()) {
                    continue;
                }
                try {
                    customer.setInQueue(false);
                    customer.setInSystem(true);
                    Thread.sleep(customer.getServiceTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customer.setServed(true);
                customer.setInQueue(false);
                customer.setInQueue(false);
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
