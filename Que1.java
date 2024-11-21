import java.util.Arrays;
import java.util.Random;

public class Que1 {
    
    // QuickSort Implementation
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

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

    // MergeSort Implementation
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    // Main Function for Testing and Timing
    public static void main(String[] args) {
        Random random = new Random();
        int[] data = random.ints(500, 1, 1001).toArray(); // Random array of size 500
        int[] quickData = Arrays.copyOf(data, data.length);
        int[] mergeData = Arrays.copyOf(data, data.length);
    
        // Display original data
        System.out.println("Original 500 random values:");
        System.out.println(Arrays.toString(data));
    
        // Measure QuickSort Time
        long startQuick = System.nanoTime();
        quickSort(quickData, 0, quickData.length - 1);
        long endQuick = System.nanoTime();
        double quickTime = (endQuick - startQuick) / 1e6; // Convert to milliseconds
    
        // Measure MergeSort Time
        long startMerge = System.nanoTime();
        mergeSort(mergeData, 0, mergeData.length - 1);
        long endMerge = System.nanoTime();
        double mergeTime = (endMerge - startMerge) / 1e6; // Convert to milliseconds
    
        // Output Results
        System.out.println("\nQuickSort Time: " + quickTime + " ms");
        System.out.println("MergeSort Time: " + mergeTime + " ms");
    
        // Display sorted results
        System.out.println("\nSorted values after QuickSort:");
        System.out.println(Arrays.toString(quickData));
    
        System.out.println("\nSorted values after MergeSort:");
        System.out.println(Arrays.toString(mergeData));
    }
    
    
}
//Time complexity: The time complexity of merge sort is always O(n log n), while the time complexity of quicksort varies between O(n log n) in the best case to O(n2) in the worst case. 
// Space complexity: Quick sort has a space complexity of O(log n), while merge sort requires O(n) additional space to merge the sorted sub-arrays.
