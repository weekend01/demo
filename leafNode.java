import java.util.ArrayList;
import java.util.List;

class Node {
    int level;         // Depth of the node in the tree
    int cost;          // Cost of the path so far
    int[] path;        // Path taken so far
    boolean[] visited; // Mark visited cities

    Node(int level, int cost, int[] path, boolean[] visited) {
        this.level = level;
        this.cost = cost;
        this.path = path.clone();
        this.visited = visited.clone();
    }
}

public class leafNode {

    // Function to generate the start (first) node
    public Node generateStartNode(int n) {
        int[] path = new int[n + 1]; // Path array (last element loops back to start)
        boolean[] visited = new boolean[n];
        path[0] = 0;                // Start at city 0
        visited[0] = true;          // Mark city 0 as visited

        return new Node(1, 0, path, visited);
    }

    // Function to generate all children of a given node
    public List<Node> generateChildren(Node currentNode, int[][] graph) {
        List<Node> children = new ArrayList<>();
        int n = graph.length;
        int currentCity = currentNode.path[currentNode.level - 1];

        for (int nextCity = 0; nextCity < n; nextCity++) {
            if (!currentNode.visited[nextCity] && graph[currentCity][nextCity] != 0) {
                int[] newPath = currentNode.path.clone();
                boolean[] newVisited = currentNode.visited.clone();
                newPath[currentNode.level] = nextCity;
                newVisited[nextCity] = true;

                int newCost = currentNode.cost + graph[currentCity][nextCity];
                children.add(new Node(currentNode.level + 1, newCost, newPath, newVisited));
            }
        }

        return children;
    }

    // Function to check whether a given node is a leaf node
    public boolean isLeafNode(Node node, int n) {
        return node.level == n; // A node is a leaf when all cities have been visited
    }

    // Example usage
    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        leafNode tsp = new leafNode();
        int n = graph.length;

        // Generate the start node
        Node startNode = tsp.generateStartNode(n);
        System.out.println("Start Node: Level = " + startNode.level + ", Cost = " + startNode.cost);

        // Generate children of the start node
        List<Node> children = tsp.generateChildren(startNode, graph);
        System.out.println("Children of Start Node:");
        for (Node child : children) {
            System.out.println("Level = " + child.level + ", Cost = " + child.cost + ", Path = " + java.util.Arrays.toString(child.path));
        }

        // Check if a child node is a leaf
        Node testNode = children.get(0);
        System.out.println("Is Leaf Node: " + tsp.isLeafNode(testNode, n));
    }
}