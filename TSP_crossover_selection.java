import java.util.*;

public class TSP_crossover_selection {

    // Distance matrix (Example: distances between cities)
    private static int[][] distanceMatrix = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    public static void main(String[] args) {
        // Sample parents for testing crossover and selection
        int[] parent1 = {0, 1, 2, 3};
        int[] parent2 = {3, 2, 1, 0};

        // Test Crossover
        int[] child = crossover(parent1, parent2);
        System.out.println("Child after Crossover: " + Arrays.toString(child));

        // Test Roulette Wheel Selection
        List<int[]> population = new ArrayList<>();
        population.add(parent1);
        population.add(parent2);
        int[] selectedParent = rouletteWheelSelection(population);  // Roulette wheel selection
        System.out.println("Selected Parent: " + Arrays.toString(selectedParent));
    }

    // Crossover using Order Crossover (OX) technique
    public static int[] crossover(int[] parent1, int[] parent2) {
        int n = parent1.length;  // Number of cities
        int[] child = new int[n];  // Initialize child array

        // Step 1: Randomly select start and end positions for crossover
        Random rand = new Random();
        int start = rand.nextInt(n);
        int end = rand.nextInt(n);

        // Ensure start < end
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Step 2: Copy the segment from parent1 to child
        Arrays.fill(child, -1);  // Fill with -1 indicating unassigned cities
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
        }

        // Step 3: Fill the remaining positions from parent2
        Set<Integer> childCities = new HashSet<>(); // Use Set to track cities already added to child
        for (int i = start; i <= end; i++) {
            childCities.add(child[i]);  // Add the cities from parent1 to the Set
        }

        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            if (child[i] == -1) {
                // Find the next city from parent2 that is not in the child
                while (childCities.contains(parent2[currentIndex])) {
                    currentIndex++;
                }
                child[i] = parent2[currentIndex];
                childCities.add(parent2[currentIndex]); // Add the city to the Set
            }
        }

        return child;
    }

    // Roulette Wheel Selection
    public static int[] rouletteWheelSelection(List<int[]> population) {
        // Calculate total fitness (sum of the inverse of tour distances)
        double totalFitness = 0;
        List<Double> fitnessList = new ArrayList<>();
        for (int[] individual : population) {
            double fitness = 1.0 / (getFitness(individual) + 1);  // Inverse of the fitness (distance)
            fitnessList.add(fitness);
            totalFitness += fitness;
        }

        // Select a parent based on a random spin of the roulette wheel
        Random rand = new Random();
        double randValue = rand.nextDouble() * totalFitness;
        double cumulativeFitness = 0.0;

        // Select the parent by accumulating fitness values
        for (int i = 0; i < population.size(); i++) {
            cumulativeFitness += fitnessList.get(i);
            if (cumulativeFitness >= randValue) {
                return population.get(i);
            }
        }

        return population.get(population.size() - 1);  // Fallback, return the last individual
    }

    // Function to calculate the total distance of a tour (fitness)
    private static int getFitness(int[] tour) {
        int fitness = 0;

        // Sum the distances between consecutive cities in the tour
        for (int i = 0; i < tour.length - 1; i++) {
            fitness += distanceMatrix[tour[i]][tour[i + 1]];
        }

        // Add the distance from the last city to the first city (to complete the tour)
        fitness += distanceMatrix[tour[tour.length - 1]][tour[0]];

        return fitness;
    }
}
