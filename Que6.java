import java.util.Arrays;

public class Que6 {

    private int boardSize;
    private char[][] board;

    public Que6(int n) {
        this.boardSize = n;
        this.board = new char[n][n];
        
        // Initialize board with empty spaces
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.'); // '.' represents an empty space
        }
    }

    // The isSafe function checks if it's safe to place a queen at (row, col)
    public boolean isSafe(int row, int col, char[][] board) {
        // Check horizontal
        for (int j = 0; j < board.length; j++) {
            if (board[row][j] == 'Q') {
                return false;
            }
        }

        // Check vertical
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // Check upper left diagonal
        int r = row;
        for (int c = col; c >= 0 && r >= 0; c--, r--) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        // Check upper right diagonal
        r = row;
        for (int c = col; c < board.length && r >= 0; r--, c++) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        // Check lower left diagonal
        r = row;
        for (int c = col; c >= 0 && r < board.length; r++, c--) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        // Check lower right diagonal
        for (int c = col; c < board.length && r < board.length; c++, r++) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        return true; // All checks passed, it's safe
    }

    // The solveNQueens function places queens row by row
    public boolean solveNQueens(int col) {
        if (col >= boardSize) {
            return true; // All queens are placed
        }

        for (int i = 0; i < boardSize; i++) {
            if (isSafe(i, col, board)) {
                board[i][col] = 'Q'; // Place queen

                // Recur to place the rest of the queens
                if (solveNQueens(col + 1)) {
                    return true;
                }

                // Backtrack if no solution found
                board[i][col] = '.'; // Remove queen
            }
        }

        return false; // No solution found for this column, backtrack
    }

    // The function to display the board
    public void solveAndDisplay() {
        if (solveNQueens(0)) {
            for (int i = 0; i < boardSize; i++) {
                System.out.println(Arrays.toString(board[i]));
            }
        } else {
            System.out.println("No solution exists for N = " + boardSize);
        }
    }

    // Main method to test the N-Queens solution
    public static void main(String[] args) {
        int[] sizes = {4, 5, 6, 7, 8};

        for (int n : sizes) {
            System.out.println("\nSolving for N = " + n);
            long startTime = System.nanoTime(); // Start measuring time

            Que6 nQueens = new Que6(n);
            nQueens.solveAndDisplay();

            long endTime = System.nanoTime(); // End measuring time
            long duration = endTime - startTime; // Duration in nanoseconds
            System.out.println("Time taken for N = " + n + ": " + duration + " nanoseconds");
        }
    }
}
