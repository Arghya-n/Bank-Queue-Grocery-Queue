import java.util.concurrent.ThreadLocalRandom;

public class Customer {

    private final long arrivalTime;
    private final long serviceTime;
    private long queueEntryTime;
    private boolean served;
    private boolean left;
    private boolean inSystem;
    private boolean inQueue;

    public Customer(long arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = ThreadLocalRandom.current().nextInt(0, 50); // 0 to 40 milliseconds
        this.served = false;
        this.left = false;
        this.inSystem=false;
        this.inQueue=true;
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
    public void setInSystem(boolean inSystem) {
        this.inSystem = inSystem ;
    }

    public boolean isInSystem() {
        return inSystem;
    }
    public void setInQueue(boolean inSystem) {
        this.inSystem = inSystem ;
    }

    public boolean isInQueue() {
        return inSystem;
    }
}
