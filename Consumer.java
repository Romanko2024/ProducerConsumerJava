public class Consumer implements Runnable {
    private final int itemsToConsume;
    private final Manager manager;
    private final int id;

    public Consumer(int itemsToConsume, Manager manager, int id) {
        this.itemsToConsume = itemsToConsume;
        this.manager = manager;
        this.id = id;
        System.out.println("Споживач " + id + " створений. Квота: " + itemsToConsume);
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToConsume; i++) {
            try {
                manager.fullSlots.acquire();
                manager.access.acquire();

                String item = manager.storage.remove(0);
                System.out.println("Consumer " + id + " took: " + item);

                manager.access.release();
                manager.emptySlots.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        manager.done.release();
    }
}