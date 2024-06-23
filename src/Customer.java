import java.util.concurrent.ThreadLocalRandom;

public class Customer {
    private final long arrivalTime;
    private final long serviceTime;
    private long queueEntryTime;
    private boolean served;
    private boolean left;

    public Customer(long arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = ThreadLocalRandom.current().nextInt(5, 11); // 5 to 10 milliseconds
        this.served = false;
        this.left = false;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setQueueEntryTime(long queueEntryTime) {
        this.queueEntryTime = queueEntryTime;
    }

    public long getQueueEntryTime() {
        return queueEntryTime;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public boolean isServed() {
        return served;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean hasLeft() {
        return left;
    }
}
