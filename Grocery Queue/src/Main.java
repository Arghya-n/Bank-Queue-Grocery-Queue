//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int simulationTime = 10; // in seconds
        int groceryQueues = 3;
        int groceryQueueLength = 10;

        QueueSimulator simulator = new QueueSimulator(simulationTime, groceryQueues, groceryQueueLength);
        simulator.simulate();
    }
}