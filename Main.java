import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть місткість сховища: ");
        int storageSize = scanner.nextInt();
        System.out.print("Введіть загальну кількість продукції: ");
        int totalItems = scanner.nextInt();
        System.out.print("Введіть кількість виробників: ");
        int producersCount = scanner.nextInt();
        System.out.print("Введіть кількість споживачів: ");
        int consumersCount = scanner.nextInt();

        Manager manager = new Manager(storageSize);

        int itemsPerProducer = totalItems / producersCount;
        int prodRemainder = totalItems % producersCount;

        int itemsPerConsumer = totalItems / consumersCount;
        int consRemainder = totalItems % consumersCount;

        //запуск producers
        for (int i = 0; i < producersCount; i++) {
            int work = itemsPerProducer + (i == 0 ? prodRemainder : 0);
            new Thread(new Producer(work, manager, i)).start();
        }

        // запуск consumers
        for (int i = 0; i < consumersCount; i++) {
            int work = itemsPerConsumer + (i == 0 ? consRemainder : 0);
            new Thread(new Consumer(work, manager, i)).start();
        }

        int totalThreads = producersCount + consumersCount;
        for (int i = 0; i < totalThreads; i++) {
            manager.done.acquire();
        }

        System.out.println("Усі потоки завершили роботу.");
    }
}
