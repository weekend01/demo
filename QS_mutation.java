import java.util.Arrays;
import java.util.Random;

public class QS_mutation {

    // Quick Sort Implementation
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1; // Pointer for greater element

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Generate Best-Case Scenario Array
    public static int[] generateBestCase(int size) {
        int[] arr = generateSortedArray(size);
        return arr;
    }

    // Generate Worst-Case Scenario Array
    public static int[] generateWorstCase(int size) {
        int[] arr = generateSortedArray(size);
        return reverseArray(arr);
    }

    private static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }

    private static int[] reverseArray(int[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    // Generate Random Array
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10);
        }
        return arr;
    }

    // Main Method
    public static void main(String[] args) {
        int size = 1000; // Size of the input
        int[] bestCase = generateBestCase(size);
        int[] worstCase = generateWorstCase(size);
        int[] randomCase = generateRandomArray(size);

        // Best-Case Time
        int[] bestCaseCopy = bestCase.clone();
        long startBest = System.nanoTime();
        quickSort(bestCaseCopy, 0, bestCaseCopy.length - 1);
        long endBest = System.nanoTime();
        System.out.println("Best Case Time: " + (endBest - startBest) + " ns");

        // Worst-Case Time
        int[] worstCaseCopy = worstCase.clone();
        long startWorst = System.nanoTime();
        quickSort(worstCaseCopy, 0, worstCaseCopy.length - 1);
        long endWorst = System.nanoTime();
        System.out.println("Worst Case Time: " + (endWorst - startWorst) + " ns");

        // Random Case Time
        int[] randomCaseCopy = randomCase.clone();
        long startRandom = System.nanoTime();
        quickSort(randomCaseCopy, 0, randomCaseCopy.length - 1);
        long endRandom = System.nanoTime();
        System.out.println("Random Case Time: " + (endRandom - startRandom) + " ns");
    }
}

// {
// import java.util.Random;
// import java.util.Scanner;

// public class TSPMutation {

//     // Function to mutate a chromosome by swapping two cities
//     public static void mutateChromosome(int[] chromosome) {
//         Random rand = new Random();

//         int index1 = rand.nextInt(chromosome.length);
//         int index2 = rand.nextInt(chromosome.length);

//         // Ensure that index1 and index2 are different
//         while (index1 == index2) {
//             index2 = rand.nextInt(chromosome.length);
//         }

//         // Swap the cities at these two positions
//         int temp = chromosome[index1];
//         chromosome[index1] = chromosome[index2];
//         chromosome[index2] = temp;
//     }

//     // Function to print the chromosome
//     public static void printChromosome(int[] chromosome) {
//         for (int city : chromosome) {
//             System.out.print(city + " ");
//         }
//         System.out.println();
//     }

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Input: Number of cities (n)
//         System.out.print("Enter the number of cities: ");
//         int n = scanner.nextInt();

//         // Input: Distance matrix
//         int[][] distanceMatrix = new int[n][n];
//         System.out.println("Enter the distance matrix: ");
//         for (int i = 0; i < n; i++) {
//             for (int j = 0; j < n; j++) {
//                 distanceMatrix[i][j] = scanner.nextInt();
//             }
//         }

//         // Generate a chromosome representing a solution (tour)
//         int[] chromosome = new int[n];
//         for (int i = 0; i < n; i++) {
//             chromosome[i] = i;  // Initially the cities are in order
//         }

//         // Print original chromosome (tour)
//         System.out.println("Original chromosome (tour): ");
//         printChromosome(chromosome);

//         // Mutate the chromosome
//         mutateChromosome(chromosome);

//         // Print mutated chromosome (tour)
//         System.out.println("Mutated chromosome (tour): ");
//         printChromosome(chromosome);

//         scanner.close();
//     }
// }
// }