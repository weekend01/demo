import java.util.Scanner;

public class Que5 {
    // Function to solve 0/1 Knapsack using DP
    public static int knapsack(int[] weights, int[] profits, int capacity, int n) {
        int[][] dp = new int[n + 1][capacity + 1];

        // Build the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(profits[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Return the maximum profit
        return dp[n][capacity];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of items
        System.out.println("Enter the number of items:");
        int n = scanner.nextInt();

        // Input profits
        int[] profits = new int[n];
        System.out.println("Enter the profits of the items:");
        for (int i = 0; i < n; i++) {
            profits[i] = scanner.nextInt();
        }

        // Input weights
        int[] weights = new int[n];
        System.out.println("Enter the weights of the items:");
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Input capacity
        System.out.println("Enter the capacity of the knapsack:");
        int capacity = scanner.nextInt();

        // Solve knapsack problem
        int maxProfit = knapsack(weights, profits, capacity, n);

        // Output the results
        System.out.println("\n------------------- 0/1 Knapsack Problem -------------------");
        System.out.println("Profits: ");
        for (int profit : profits) {
            System.out.print(profit + " ");
        }
        System.out.println("\nWeights: ");
        for (int weight : weights) {
            System.out.print(weight + " ");
        }
        System.out.println("\nCapacity: " + capacity);
        System.out.println("\nMaximum Profit: " + maxProfit);

        scanner.close();
    }
}
