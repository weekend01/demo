import java.util.Arrays;

public class TSP_BB_GA {

    private static final int INF = Integer.MAX_VALUE;
    private int[][] graph;
    private int n;
    private int minCost;
    private int[] bestPath;

    public TSP_BB_GA(int[][] graph) {
        this.graph = graph;
        this.n = graph.length;
        this.minCost = INF;
        this.bestPath = new int[n + 1];
    }

    public void solveTSP() {
        int[] currPath = new int[n + 1];
        boolean[] visited = new boolean[n];

        Arrays.fill(currPath, -1);
        Arrays.fill(visited, false);

        currPath[0] = 0;
        visited[0] = true;

        branchAndBound(0, 1, 0, currPath, visited);

        System.out.println("Minimum cost: " + minCost);
        System.out.print("Path: ");
        for (int i : bestPath) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private void branchAndBound(int currCost, int level, int currBound, int[] currPath, boolean[] visited) {
        if (level == n) {
            if (graph[currPath[level - 1]][currPath[0]] != 0) {
                int totalCost = currCost + graph[currPath[level - 1]][currPath[0]];
                if (totalCost < minCost) {
                    minCost = totalCost;
                    System.arraycopy(currPath, 0, bestPath, 0, n);
                    bestPath[n] = currPath[0];
                }
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (graph[currPath[level - 1]][i] != 0 && !visited[i]) {
                int tempBound = currBound;
                int tempCost = currCost;

                currCost += graph[currPath[level - 1]][i];
                currBound -= getLowerBoundReduction(currPath[level - 1], i);

                if (currCost + currBound < minCost) {
                    currPath[level] = i;
                    visited[i] = true;

                    branchAndBound(currCost, level + 1, currBound, currPath, visited);
                }

                currCost = tempCost;
                currBound = tempBound;
                Arrays.fill(visited, false);
                for (int j = 0; j < level; j++) {
                    visited[currPath[j]] = true;
                }
            }
        }
    }

    private int getLowerBoundReduction(int from, int to) {
        int reduction = 0;
        for (int i = 0; i < n; i++) {
            if (graph[from][i] != INF) {
                reduction += graph[from][i];
            }
            if (graph[to][i] != INF) {
                reduction += graph[to][i];
            }
        }
        return reduction / 2;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        TSP_BB_GA tsp = new TSP_BB_GA(graph);
        tsp.solveTSP();
    }
}


// import java.util.*;

// public class TSP_BB_GA{

//     private static final Random random = new Random();
//     private int[][] graph;
//     private int populationSize;
//     private int generations;
//     private double mutationRate;

//     public TSP_BB_GA(int[][] graph, int populationSize, int generations, double mutationRate) {
//         this.graph = graph;
//         this.populationSize = populationSize;
//         this.generations = generations;
//         this.mutationRate = mutationRate;
//     }

//     public void solveTSP() {
//         List<int[]> population = initializePopulation();
//         int[] bestPath = null;
//         int bestCost = Integer.MAX_VALUE;

//         for (int gen = 0; gen < generations; gen++) {
//             population = evolvePopulation(population);

//             for (int[] individual : population) {
//                 int cost = calculateCost(individual);
//                 if (cost < bestCost) {
//                     bestCost = cost;
//                     bestPath = individual.clone();
//                 }
//             }

//             System.out.println("Generation " + (gen + 1) + ": Best cost = " + bestCost);
//         }

//         System.out.println("Final Best Cost: " + bestCost);
//         System.out.println("Best Path: " + Arrays.toString(bestPath));
//     }

//     private List<int[]> initializePopulation() {
//         List<int[]> population = new ArrayList<>();
//         int n = graph.length;

//         for (int i = 0; i < populationSize; i++) {
//             int[] individual = new int[n];
//             for (int j = 0; j < n; j++) {
//                 individual[j] = j;
//             }
//             shuffleArray(individual);
//             population.add(individual);
//         }

//         return population;
//     }

//     private void shuffleArray(int[] array) {
//         for (int i = array.length - 1; i > 0; i--) {
//             int index = random.nextInt(i + 1);
//             int temp = array[index];
//             array[index] = array[i];
//             array[i] = temp;
//         }
//     }

//     private List<int[]> evolvePopulation(List<int[]> population) {
//         List<int[]> newPopulation = new ArrayList<>();
//         for (int i = 0; i < population.size(); i++) {
//             int[] parent1 = selectParent(population);
//             int[] parent2 = selectParent(population);
//             int[] offspring = crossover(parent1, parent2);
//             mutate(offspring);
//             newPopulation.add(offspring);
//         }
//         return newPopulation;
//     }

//     private int[] selectParent(List<int[]> population) {
//         int tournamentSize = 5;
//         List<int[]> tournament = new ArrayList<>();

//         for (int i = 0; i < tournamentSize; i++) {
//             tournament.add(population.get(random.nextInt(population.size())));
//         }

//         return tournament.stream().min(Comparator.comparingInt(this::calculateCost)).orElse(null);
//     }

//     private int[] crossover(int[] parent1, int[] parent2) {
//         int n = parent1.length;
//         int start = random.nextInt(n);
//         int end = start + random.nextInt(n - start);

//         int[] offspring = new int[n];
//         Arrays.fill(offspring, -1);

//         for (int i = start; i < end; i++) {
//             offspring[i] = parent1[i];
//         }

//         int currentIndex = end % n;
//         for (int gene : parent2) {
//             if (!contains(offspring, gene)) {
//                 offspring[currentIndex] = gene;
//                 currentIndex = (currentIndex + 1) % n;
//             }
//         }

//         return offspring;
//     }

//     private void mutate(int[] individual) {
//         if (random.nextDouble() < mutationRate) {
//             int i = random.nextInt(individual.length);
//             int j = random.nextInt(individual.length);
//             int temp = individual[i];
//             individual[i] = individual[j];
//             individual[j] = temp;
//         }
//     }

//     private boolean contains(int[] array, int value) {
//         for (int elem : array) {
//             if (elem == value) {
//                 return true;
//             }
//         }
//         return false;
//     }

//     private int calculateCost(int[] path) {
//         int cost = 0;
//         for (int i = 0; i < path.length - 1; i++) {
//             cost += graph[path[i]][path[i + 1]];
//         }
//         cost += graph[path[path.length - 1]][path[0]]; // Return to start
//         return cost;
//     }

//     public static void main(String[] args) {
//         int[][] graph = {
//                 {0, 10, 15, 20},
//                 {10, 0, 35, 25},
//                 {15, 35, 0, 30},
//                 {20, 25, 30, 0}
//         };

//         TSP_BB_GA tspGA = new TSP_BB_GA(graph, 100, 500, 0.05);
//         tspGA.solveTSP();
//     }
// }