import java.util.concurrent.ThreadLocalRandom;

public class Customer {
    private final long arrivalTime;
    private final int serviceTime;
    private boolean served;
    private boolean left;
    private long queueEntryTime;

    public Customer(long arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = ThreadLocalRandom.current().nextInt(5, 11); // 5 to 10 milliseconds
        this.served = false;
        this.left = false;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public boolean hasLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public long getQueueEntryTime() {
        return queueEntryTime;
    }

    public void setQueueEntryTime(long queueEntryTime) {
        this.queueEntryTime = queueEntryTime;
    }
}
