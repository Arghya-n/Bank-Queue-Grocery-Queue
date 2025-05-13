


public class Main {
    public static void main(String[] args) {
        int simulationTime = 10; // 10 seconds
        QueueSimulator simulator = new QueueSimulator(simulationTime, 3, 10);
        simulator.simulate();
    }
}
