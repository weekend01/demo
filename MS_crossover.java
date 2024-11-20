// import java.util.Arrays;
// import java.util.Random;
// import java.util.Scanner;

// public class Que4 {

//     // Merge Sort Implementation
//     public static void mergeSort(int[] arr) {
//         if (arr.length <= 1) {
//             return;
//         }

//         int mid = arr.length / 2;
//         int[] left = Arrays.copyOfRange(arr, 0, mid);
//         int[] right = Arrays.copyOfRange(arr, mid, arr.length);

//         mergeSort(left);
//         mergeSort(right);

//         merge(arr, left, right);
//     }

//     private static void merge(int[] arr, int[] left, int[] right) {
//         int i = 0, j = 0, k = 0;

//         // Merge the left and right arrays into arr
//         while (i < left.length && j < right.length) {
//             if (left[i] <= right[j]) {
//                 arr[k++] = left[i++];
//             } else {
//                 arr[k++] = right[j++];
//             }
//         }

//         // Copy remaining elements if any
//         while (i < left.length) {
//             arr[k++] = left[i++];
//         }
//         while (j < right.length) {
//             arr[k++] = right[j++];
//         }
//     }

//     // Generate Best-Case Scenario Array (Already Sorted)
//     public static int[] generateBestCase(int size) {
//         int[] arr = generateSortedArray(size);
//         return arr;
//     }

//     // Generate Worst-Case Scenario Array (Reverse Sorted)
//     public static int[] generateWorstCase(int size) {
//         int[] arr = generateSortedArray(size);
//         return reverseArray(arr);
//     }

//     private static int[] generateSortedArray(int size) {
//         int[] arr = new int[size];
//         for (int i = 0; i < size; i++) {
//             arr[i] = i;
//         }
//         return arr;
//     }

//     private static int[] reverseArray(int[] arr) {
//         for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
//             int temp = arr[i];
//             arr[i] = arr[j];
//             arr[j] = temp;
//         }
//         return arr;
//     }

//     // Generate Random Array
//     public static int[] generateRandomArray(int size) {
//         Random random = new Random();
//         int[] arr = new int[size];
//         for (int i = 0; i < size; i++) {
//             arr[i] = random.nextInt(size * 10); // Random values up to 10 times the size
//         }
//         return arr;
//     }

//     // Measure Execution Time
//     public static long measureSortTime(int[] array) {
//         int[] copy = array.clone();
//         long start = System.nanoTime();
//         mergeSort(copy);
//         long end = System.nanoTime();
//         return end - start;
//     }

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
        
//         System.out.print("Enter the size of the array: ");
//         int size = scanner.nextInt();
        
//         int iterations = 5; // Number of iterations for averaging

//         // Best Case
//         int[] bestCase = generateBestCase(size);
//         long bestTime = 0;
//         for (int i = 0; i < iterations; i++) {
//             bestTime += measureSortTime(bestCase);
//         }
//         System.out.println("Best Case Time (avg): " + (bestTime / iterations) + " ns");

//         // Worst Case
//         int[] worstCase = generateWorstCase(size);
//         long worstTime = 0;
//         for (int i = 0; i < iterations; i++) {
//             worstTime += measureSortTime(worstCase);
//         }
//         System.out.println("Worst Case Time (avg): " + (worstTime / iterations) + " ns");

//         // Random Case
//         int[] randomCase = generateRandomArray(size);
//         long randomTime = 0;
//         for (int i = 0; i < iterations; i++) {
//             randomTime += measureSortTime(randomCase);
//         }
//         System.out.println("Random Case Time (avg): " + (randomTime / iterations) + " ns");

//         scanner.close();
//     }
// }

import java.util.*;

public class MS_crossover {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of cities: ");
        int n = sc.nextInt();

        int[] parent1 = new int[n];
        int[] parent2 = new int[n];

        System.out.println("Enter the Parent 1 Chromosome (tour): ");
        for (int i = 0; i < n; i++) {
            parent1[i] = sc.nextInt();
        }

        System.out.println("Enter the Parent 2 Chromosome (tour): ");
        for (int i = 0; i < n; i++) {
            parent2[i] = sc.nextInt();
        }

        int[] child = crossover(parent1, parent2);

        System.out.println("Parent 1 Chromosome: ");
        System.out.println(Arrays.toString(parent1));

        System.out.println("Parent 2 Chromosome: ");
        System.out.println(Arrays.toString(parent2));

        System.out.println("Child Chromosome after Crossover: ");
        System.out.println(Arrays.toString(child));

        sc.close();
    }

    public static int[] crossover(int[] parent1, int[] parent2) {
        int n = parent1.length;
        int[] child = new int[n];
        Arrays.fill(child, -1); // Initialize child array with -1

        Random rand = new Random();
        int start = rand.nextInt(n);  // Random start point
        int end = rand.nextInt(n);    // Random end point

        // Ensure start < end
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Copy segment from Parent 1 to Child
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
        }

        // Fill remaining positions from Parent 2, maintaining order
        HashSet<Integer> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            set.add(child[i]);
        }

        int childIndex = 0;
        for (int i = 0; i < n; i++) {
            if (child[i] == -1) {
                // Fill from Parent 2
                while (set.contains(parent2[childIndex])) {
                    childIndex++;
                }
                child[i] = parent2[childIndex++];
            }
        }

        return child;
    }
}