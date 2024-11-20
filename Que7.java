import java.util.*;

public class Que7 {
//7B
    static class City {
        int x, y;

        City(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double distanceTo(City other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }
    }

    static class Tour {
        List<City> cities;
        double fitness;

        Tour(List<City> cities) {
            this.cities = new ArrayList<>(cities);
            Collections.shuffle(this.cities); // Random initial tour
            calculateFitness();
        }

        void calculateFitness() {
            double totalDistance = 0;
            for (int i = 0; i < cities.size() - 1; i++) {
                totalDistance += cities.get(i).distanceTo(cities.get(i + 1));
            }
            totalDistance += cities.get(cities.size() - 1).distanceTo(cities.get(0)); // Return to start
            fitness = 1 / totalDistance; // Higher fitness for shorter tours
        }

        double getDistance() {
            return 1 / fitness; // Inverse of fitness
        }
    }

    public static void main(String[] args) {
        int cityCount = 5;
        int populationSize = 10;
        int generations = 30;
        double mutationRate = 0.15;

        // 1. Create random cities
        List<City> cities = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < cityCount; i++) {
            cities.add(new City(rand.nextInt(200), rand.nextInt(200)));
        }

        // 2. Initialize population
        List<Tour> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Tour(cities));
        }

        for (int gen = 0; gen < generations; gen++) {
            // 3.  Evaluate fitness
            population.forEach(Tour::calculateFitness);
            population.sort(Comparator.comparingDouble(t -> -t.fitness)); // Sort by fitness (descending)

            // 4. Selection: Elitism (keep top 10%)
            List<Tour> newPopulation = new ArrayList<>(population.subList(0, populationSize / 10));

            // Crossover
            Random random = new Random();
            while (newPopulation.size() < populationSize) {
                Tour parent1 = selectParent(population);
                Tour parent2 = selectParent(population);
                Tour child = crossover(parent1, parent2);
                newPopulation.add(child);
            }

            // Mutation
            for (Tour tour : newPopulation) {
                if (random.nextDouble() < mutationRate) {
                    mutate(tour);
                }
            }

            // Replace old population
            population = newPopulation;

            // Print best tour of this generation
            Tour bestTour = population.get(0);
            System.out.println("Generation " + gen + ": Best distance = " + bestTour.getDistance());
        }

        // Final result
        Tour bestTour = population.get(0);
        System.out.println("Final Best Tour Distance: " + bestTour.getDistance());
        System.out.println("Cities Order: ");
        bestTour.cities.forEach(city -> System.out.println("(" + city.x + ", " + city.y + ")"));
    }

    // Parent selection using roulette wheel
    private static Tour selectParent(List<Tour> population) {
        double totalFitness = population.stream().mapToDouble(t -> t.fitness).sum();
        double rand = Math.random() * totalFitness;
        double cumulativeFitness = 0;
        for (Tour tour : population) {
            cumulativeFitness += tour.fitness;
            if (cumulativeFitness >= rand) {
                return tour;
            }
        }
        return population.get(0); // Fallback
    }

    // Crossover using ordered crossover (OX)
    private static Tour crossover(Tour parent1, Tour parent2) {
        int n = parent1.cities.size();
        List<City> childCities = new ArrayList<>(Collections.nCopies(n, null));

        Random rand = new Random();
        int start = rand.nextInt(n);
        int end = rand.nextInt(n);

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Copy segment from Parent 1
        for (int i = start; i <= end; i++) {
            childCities.set(i, parent1.cities.get(i));
        }

        // Fill remaining from Parent 2
        int currentIndex = 0;
        for (City city : parent2.cities) {
            if (!childCities.contains(city)) {
                while (childCities.get(currentIndex) != null) {
                    currentIndex++;
                }
                childCities.set(currentIndex, city);
            }
        }

        return new Tour(childCities);
    }

    // Mutation: Swap two cities
    private static void mutate(Tour tour) {
        Random rand = new Random();
        int i = rand.nextInt(tour.cities.size());
        int j = rand.nextInt(tour.cities.size());
        Collections.swap(tour.cities, i, j);
        tour.calculateFitness();
    }
}
