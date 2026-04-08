import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Manager {
    public Semaphore access = new Semaphore(1);
    public Semaphore emptySlots;
    public Semaphore fullSlots;
    public Semaphore done = new Semaphore(0);

    public ArrayList<String> storage = new ArrayList<>();

    public Manager(int storageSize) {
        this.emptySlots = new Semaphore(storageSize);
        this.fullSlots = new Semaphore(0);
    }
}
