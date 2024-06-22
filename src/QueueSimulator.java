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
    }

    private void printStatistics() {
        long totalServedTime = 0;
        int totalCustomers = 0;
        int servedCustomers = 0;
        int leftCustomers = 0;

        for (Customer customer : customers) {
            totalCustomers++;
            if (customer.isServed()) {
                servedCustomers++;

                totalServedTime += customer.getServiceTime();
            }
            if (customer.hasLeft()) {
                leftCustomers++;
            }
        }

        System.out.println("Total customers: " + totalCustomers);
        System.out.println("Customers served: " + servedCustomers);
        System.out.println("Customers left: " + leftCustomers);
        System.out.println("Average service time: " + (servedCustomers > 0 ? (totalServedTime / servedCustomers) : 0) + " milliseconds");
    }

    public static void main(String[] args) {
        int simulationTime = 10; // 10 seconds
        QueueSimulator simulator = new QueueSimulator(simulationTime, 3, 10);
        simulator.simulate();
    }
}
