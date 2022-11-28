import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Simulation {
    private static ArrayList<Fish> pond = new ArrayList<>();
    private static AtomicInteger population = new AtomicInteger(0);

    private static void setup(int nFishes) {
        // Initialize the pond with n fish
        for (int i = 0; i < nFishes; i++) {
            pond.add(new Fish(i % 2 == 0));
        }
        population.updateAndGet(value -> nFishes);
    }

    private static List<Integer> fishIndices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < pond.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        return indices;
    }

    public static void main(String[] args) {
        setup(20);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        // Create meetings
        while (pond.size() > 1) {
            List<Integer> indices = fishIndices();
            int nFish = pond.size();
            // Create meetings for every fish (excluding newborns)
            for (int i = 0; i < nFish; i += 2) {

                int finalI = i;
                executor.submit(new Thread(() -> {
                    Meeting m = new Meeting(pond.get(indices.get(finalI)), pond.get(indices.get(finalI + 1)));
                    int populationChange = m.meet();
                    if (populationChange == 2) {
                        synchronized (pond) {
                            pond.add(new Fish());
                            pond.add(new Fish());
                        }
                    }
                    population.updateAndGet(population -> population + populationChange);
                    System.out.println(population.get());
                }));
            }
            while (executor.getActiveCount() > 0) ;
            // Remove dead fish
            synchronized (pond) {
                pond = (ArrayList<Fish>) pond.stream().filter(fish -> fish.isAlive()).collect(Collectors.toList());
            }
        }
        executor.shutdown();
    }
}
