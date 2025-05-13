import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QueueSimulator {

    private final GroceryQueues groceryQueues;
    private final List<Customer> customers;
    private final int simulationTime;
    private long clock;
    private long nextGroceryCustomerArrival;

    public QueueSimulator(int simulationTime, int groceryQueues, int groceryQueueLength) {
        this.simulationTime = simulationTime * 1000; // convert seconds to milliseconds

        this.groceryQueues = new GroceryQueues(groceryQueues, groceryQueueLength);
        this.customers = new ArrayList<>();
        this.nextGroceryCustomerArrival = getRandomArrivalTime();
    }

    private long getRandomArrivalTime() {
        return ThreadLocalRandom.current().nextInt(0, 4); // random time between 0 and 3 milliseconds
    }

    public void simulate() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + simulationTime;

        while (System.currentTimeMillis() < endTime) {
            clock = System.currentTimeMillis() - startTime;
            if (clock >= nextGroceryCustomerArrival) {
                Customer customer = new Customer(clock);
                if (!groceryQueues.addCustomer(customer)) {
                    customer.setLeft(true);
                }
                customers.add(customer);
                nextGroceryCustomerArrival += getRandomArrivalTime();
            }

            try {
                Thread.sleep(1); // Simulate real-time clock in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        groceryQueues.stop();
        printStatistics();
    }

    private void printStatistics() {
        long totalServedTime = 0;
        int totalCustomers = customers.size();
        int servedCustomers = 0;
        int leftCustomers = 0;
        int custommersCurrentlyinSystem=0;
        int inQueue=0;
        int inSystem=0;

        for (Customer customer : customers) {
            if (customer.isServed()) {
                servedCustomers++;
                totalServedTime += customer.getServiceTime();
            }
            else if (customer.hasLeft()) {
                leftCustomers++;
            }
            else if(customer.isInQueue()){
                inQueue++;
            }
            else if(customer.isInSystem()){
                inSystem++;
            }
        }

        System.out.println("Total customers: " + totalCustomers);
        System.out.println("Customers served: " + servedCustomers);
        System.out.println("Customers left: " + leftCustomers);
        System.out.println("Customers in Queue: " + inQueue);
        System.out.println("Customers in System: " + inSystem);
        System.out.println("Average service time: " + (servedCustomers > 0 ? (totalServedTime*1.00/ servedCustomers) : 0) + " milliseconds");
    }

    public static void main(String[] args) {
        int simulationTime = 10; // in seconds
        int groceryQueues = 5;
        int groceryQueueLength = 10;

        QueueSimulator simulator = new QueueSimulator(simulationTime, groceryQueues, groceryQueueLength);
        simulator.simulate();
    }
}
