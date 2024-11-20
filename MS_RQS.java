import java.util.*;

/* 
PART A: Implement QuickSort and Randomized QuickSort.
PART B: Compare their time required.
*/

// Harshada Borse
public class MS_RQS {
    // Randomized QuickSort
    public static void randomizedQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = randomizedPartition(arr, low, high);
            randomizedQuickSort(arr, low, p - 1);
            randomizedQuickSort(arr, p + 1, high);
        }
    }

    public static int randomizedPartition(int[] arr, int low, int high) {
        Random rand = new Random();
        int randomIndex = low + rand.nextInt(high - low + 1); // Generate random pivot index
        swap(arr, randomIndex, high); // Swap pivot with the last element
        return partition(arr, low, high);
    }

    // Regular QuickSort
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Pivot is the last element
        int i = low - 1; // Pointer for the smaller elements

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high); // Place pivot in the correct position
        return i + 1;
    }

    // Swap utility
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        // Generate a random array of 500 values
        Random rand = new Random();
        int[] originalArray = rand.ints(5, 1, 1001).toArray(); // Values between 1 and 1000

        System.out.println("Original Array:");
        System.out.println(Arrays.toString(originalArray));

        // Copy the array for each sorting method
        int[] quickSortArray = Arrays.copyOf(originalArray, originalArray.length);
        int[] randomizedQuickSortArray = Arrays.copyOf(originalArray, originalArray.length);

        // Measure time for regular QuickSort
        long startQuick = System.nanoTime();
        quickSort(quickSortArray, 0, quickSortArray.length - 1);
        long endQuick = System.nanoTime();
        double quickSortTime = (endQuick - startQuick) / 1e6; // Convert to milliseconds

        // Measure time for Randomized QuickSort
        long startRandomQuick = System.nanoTime();
        randomizedQuickSort(randomizedQuickSortArray, 0, randomizedQuickSortArray.length - 1);
        long endRandomQuick = System.nanoTime();
        double randomizedQuickSortTime = (endRandomQuick - startRandomQuick) / 1e6; // Convert to milliseconds

        // Print results
        System.out.println("\nSorted Array (QuickSort):");
        System.out.println(Arrays.toString(quickSortArray));

        System.out.println("\nSorted Array (Randomized QuickSort):");
        System.out.println(Arrays.toString(randomizedQuickSortArray));

        System.out.println("\n------------------- Time Comparison -------------------");
        System.out.println("QuickSort Time: " + quickSortTime + " ms");
        System.out.println("Randomized QuickSort Time: " + randomizedQuickSortTime + " ms");
    }
}
