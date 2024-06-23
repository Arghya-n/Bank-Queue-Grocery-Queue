import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QueueSimulator {
    private final BankQueue bankQueue;
    private final List<Customer> customers;
    private final int simulationTime;
    private long clock;

    public QueueSimulator(int simulationTime, int bankTellers, int bankQueueLength) {
        this.simulationTime = simulationTime * 1000; // convert seconds to milliseconds
        this.bankQueue = new BankQueue(bankTellers, bankQueueLength);
        this.customers = new ArrayList<>();
    }

    public void simulate() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + simulationTime;

        while (System.currentTimeMillis() < endTime) {
            clock = System.currentTimeMillis() - startTime;
            if (ThreadLocalRandom.current().nextInt(20, 61) <= (System.currentTimeMillis() - startTime)) {
                Customer customer = new Customer(clock);
                if (!bankQueue.addCustomer(customer)) {
                    customer.setLeft(true);
                }
                customers.add(customer);
            }

            try {
                Thread.sleep(1); // Simulate real-time clock in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printStatistics();
        bankQueue.stop();
    }

    private void printStatistics() {
        long totalServedTime = 0;
        int totalCustomers = 0;
        int servedCustomers = 0;
        int leftCustomers = 0;
        int custommersCurrentlyinSystem=0;
        for (Customer customer : customers) {
            totalCustomers++;
            if (customer.isServed()) {
                servedCustomers++;

                totalServedTime += customer.getServiceTime();
            }
            else if (customer.hasLeft()) {
                leftCustomers++;
            }
            else{
                custommersCurrentlyinSystem++;
            }
        }

        System.out.println("Total customers: " + totalCustomers);
        System.out.println("Customers served: " + servedCustomers);
        System.out.println("Customers left: " + leftCustomers);
        System.out.println("Customers currently in the system: "+ custommersCurrentlyinSystem);
        System.out.println("Average service time: " + (servedCustomers > 0 ? (totalServedTime / servedCustomers) : 0) + " milliseconds");
    }


}