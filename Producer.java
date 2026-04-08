public class Producer implements Runnable {
    private final int itemsToProduce;
    private final Manager manager;
    private final int id;

    public Producer(int itemsToProduce, Manager manager, int id) {
        this.itemsToProduce = itemsToProduce;
        this.manager = manager;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            try {
                manager.emptySlots.acquire();
                manager.access.acquire();

                String item = "P" + id + "_item_" + i;
                manager.storage.add(item);
                System.out.println("Producer " + id + " added: " + item);

                manager.access.release();
                manager.fullSlots.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        manager.done.release();
    }
}
