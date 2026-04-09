import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть місткість сховища: ");
        int storageSize = scanner.nextInt();
        System.out.print("Введіть загальну кількість продукції: ");
        int totalItems = scanner.nextInt();
        System.out.print("Введіть кількість виробників: ");
        int producersCount = scanner.nextInt();
        System.out.print("Введіть кількість споживачів: ");
        int consumersCount = scanner.nextInt();

        Manager manager = new Manager(storageSize);

        int[] prodQuotas = distributeQuota(totalItems, producersCount, random);
        int[] consQuotas = distributeQuota(totalItems, consumersCount, random);

        for (int i = 0; i < producersCount; i++) {
            new Thread(new Producer(prodQuotas[i], manager, i)).start();
        }

        for (int i = 0; i < consumersCount; i++) {
            new Thread(new Consumer(consQuotas[i], manager, i)).start();
        }

        int totalThreads = producersCount + consumersCount;
        for (int i = 0; i < totalThreads; i++) {
            manager.done.acquire();
        }

        System.out.println("Усі потоки завершили роботу.");
    }

    private static int[] distributeQuota(int total, int count, Random random) {
        int[] quotas = new int[count];
        int remaining = total;

        for (int i = 0; i < count - 1; i++) {
            int maxForThisThread = remaining - (count - i - 1);
            if (maxForThisThread <= 1) {
                quotas[i] = 1;
            } else {
                quotas[i] = random.nextInt(maxForThisThread) + 1;
            }
            remaining -= quotas[i];
        }
        quotas[count - 1] = remaining;
        return quotas;
    }
}
