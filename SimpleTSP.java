import java.util.*;

public class SimpleTSP {

    // Distance matrix representing the distances between cities
    private static final int[][] distanceMatrix = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    public static void main(String[] args) {
        SimpleTSP tsp = new SimpleTSP();
        Node startNode = generateStartNode(); // Start from city 0
        tsp.solveTSP(startNode); // Solve the problem
    }

    // Node class represents a state in the branch and bound tree
    static class Node {
        List<Integer> tour;  // The current tour (cities visited so far)
        double cost;         // The total cost of the current tour
        double lowerBound;   // Lower bound estimate for the remaining cost

        // Constructor for the node
        public Node(List<Integer> tour, double cost, double lowerBound) {
            this.tour = new ArrayList<>(tour);
            this.cost = cost;
            this.lowerBound = lowerBound;
        }
    }

    // Generate the start (first) node for the LCBB TSP
    public static Node generateStartNode() {
        List<Integer> initialTour = new ArrayList<>();
        initialTour.add(0);  // Start from city 0
        double initialCost = 0.0;  // Initial cost is 0
        double lowerBound = calculateLowerBound(initialTour); // Calculate lower bound for starting node

        return new Node(initialTour, initialCost, lowerBound);
    }

    // Generate all the children of a given node (next possible moves)
    public List<Node> generateChildren(Node parent) {
        List<Node> children = new ArrayList<>();
        List<Integer> parentTour = parent.tour;

        // Set of unvisited cities
        Set<Integer> unvisitedCities = new HashSet<>();
        for (int i = 0; i < distanceMatrix.length; i++) {
            if (!parentTour.contains(i)) {
                unvisitedCities.add(i);
            }
        }

        // For each unvisited city, create a new child node
        for (int city : unvisitedCities) {
            List<Integer> newTour = new ArrayList<>(parentTour);
            newTour.add(city);  // Add the city to the tour

            double newCost = parent.cost + distanceMatrix[parentTour.get(parentTour.size() - 1)][city]; // Update cost
            double newLowerBound = calculateLowerBound(newTour); // Calculate new lower bound

            children.add(new Node(newTour, newCost, newLowerBound)); // Add the new node to the children list
        }

        return children;
    }

    // Check whether a given node is a leaf node (all cities visited)
    public boolean isLeafNode(Node node) {
        return node.tour.size() == distanceMatrix.length; // A leaf node is reached when all cities are visited
    }

    // Calculate the lower bound for the remaining cost of the tour
    public static double calculateLowerBound(List<Integer> tour) {
        double lowerBound = 0.0;
        int n = distanceMatrix.length;

        // Keep track of visited cities
        boolean[] visited = new boolean[n];
        for (int i = 0; i < tour.size(); i++) {
            visited[tour.get(i)] = true;
        }

        // Calculate the minimum edge cost for each unvisited city
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                double minEdge = Double.MAX_VALUE;
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && i != j) {
                        minEdge = Math.min(minEdge, distanceMatrix[i][j]);
                    }
                }
                lowerBound += minEdge; // Add the minimum edge cost to the lower bound
            }
        }

        return lowerBound;
    }

    // Solve the TSP using LCBB (Least Cost Branch and Bound)
    public void solveTSP(Node startNode) {
        // PriorityQueue ensures we always explore the node with the least cost/lower bound
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.lowerBound));
        pq.add(startNode);  // Add the start node to the priority queue

        // Explore the nodes until we find the solution
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll(); // Take the node with the lowest lower bound

            // If it's a leaf node, we've found a complete tour
            if (isLeafNode(currentNode)) {
                // Complete the tour by returning to the start city (city 0)
                int lastCity = currentNode.tour.get(currentNode.tour.size() - 1);
                double finalCost = currentNode.cost + distanceMatrix[lastCity][currentNode.tour.get(0)]; // Return to city 0
                System.out.println("Tour: " + currentNode.tour);
                System.out.println("Total cost: " + finalCost);
                return; // Exit after finding the solution
            }

            // Generate all children of the current node
            List<Node> children = generateChildren(currentNode);
            for (Node child : children) {
                // Only add child nodes with lower bounds smaller than the current node's cost
                if (child.lowerBound < currentNode.cost) {
                    pq.add(child);
                }
            }
        }
    }
}
