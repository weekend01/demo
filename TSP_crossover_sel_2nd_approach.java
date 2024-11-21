import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TSP_crossover_sel_2nd_approach {
    private static final Random random = new Random();

    // Function to perform crossover between two parent chromosomes
    public static int[] crossover(int[] parent1, int[] parent2) {
        int n = parent1.length;
        int[] child = new int[n];
        boolean[] visited = new boolean[n];

        // Randomly select a subsequence from parent1
        int start = random.nextInt(n);
        int end = start + random.nextInt(n - start);

        // Copy the subsequence to the child
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
            visited[parent1[i]] = true;
        }

        // Fill the remaining positions with cities from parent2
        int currentIndex = (end + 1) % n;
        for (int city : parent2) {
            if (!visited[city]) {
                child[currentIndex] = city;
                currentIndex = (currentIndex + 1) % n;
            }
        }

        return child;
    }

    // Function to select a parent chromosome from the population using tournament selection
    public static int[] selectParent(List<int[]> population, int[] fitness) {
        int tournamentSize = 5;
        List<Integer> tournament = new ArrayList<>();

        // Randomly select tournamentSize individuals
        for (int i = 0; i < tournamentSize; i++) {
            tournament.add(random.nextInt(population.size()));
        }

        // Find the individual with the best fitness in the tournament
        int bestIndex = tournament.get(0);
        for (int index : tournament) {
            if (fitness[index] < fitness[bestIndex]) { // Lower fitness indicates a better solution
                bestIndex = index;
            }
        }

        return population.get(bestIndex);
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        // Example population (each individual is a permutation of cities)
        List<int[]> population = new ArrayList<>();
        population.add(new int[]{0, 1, 2, 3});
        population.add(new int[]{0, 2, 3, 1});
        population.add(new int[]{0, 3, 1, 2});
        population.add(new int[]{0, 1, 3, 2});
        population.add(new int[]{0, 2, 1, 3});

        // Example fitness array (total path cost for each individual)
        int[] fitness = {80, 90, 100, 70, 85};

        // Perform crossover
        int[] parent1 = population.get(0);
        int[] parent2 = population.get(1);
        int[] child = crossover(parent1, parent2);

        System.out.println("Parent 1: " + java.util.Arrays.toString(parent1));
        System.out.println("Parent 2: " + java.util.Arrays.toString(parent2));
        System.out.println("Child:    " + java.util.Arrays.toString(child));

        // Perform selection
        int[] selectedParent = selectParent(population, fitness);
        System.out.println("Selected Parent: " + java.util.Arrays.toString(selectedParent));
    }
}