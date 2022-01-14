import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    private static final Random random = new Random();
    private static final int SHOPS_AMOUNT = 3;
    private static final int TIMEOUT = 2000;

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(SHOPS_AMOUNT);
        final List<int[]> shopGain = new ArrayList<>();

        for (int i = 0; i < SHOPS_AMOUNT; i++) {
            shopGain.add(arrayInit());
            System.out.printf("Покупки в магазине #%d:\n", i + 1);
            System.out.println(Arrays.toString(shopGain.get(i)));
        }

        final LongAdder gain = new LongAdder();
        for (int i = 0; i < SHOPS_AMOUNT; i++) {
            int[] totalShopsGain = shopGain.get(i);
            executorService.submit(() -> {
                Arrays.stream(totalShopsGain).forEach(gain::add);
            });
        }

        executorService.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        System.out.printf("Общая выручка со всех магазинов = %s", gain);
        executorService.shutdown();
    }

    private static int[] arrayInit() {
        final int SIZE = 10;
        final int THRESHOLD = 10;
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt(THRESHOLD);
        }
        return array;
    }
}
