import java.util.ArrayList;
import java.util.List;

public class NQueens {


    /**
     * Checks the 45° and 135° diagonals for an existing queen. For example, if the board is a 5x5
     * and you call checkDiagonal(board, 3, 1), The positions checked for an existing queen are
     * marked below with an `x`. The location (3, 1) is marked with an `o`.
     *
     * ....x
     * ...x.
     * x.x..
     * .o...
     * .....
     *
     * Returns true if a Queen is found.
     *
     * Do not modify this function (the tests use it)
     */
    public static boolean checkDiagonal(char[][] board, int r, int c) {
        int y = r - 1;
        int x = c - 1;
        while (y >= 0 && x >= 0) {
            if (board[y][x] == 'Q') return true;
            x--;
            y--;
        }
        y = r - 1;
        x = c + 1;
        while (y >= 0 && x < board[0].length) {
            if (board[y][x] == 'Q') return true;
            x++;
            y--;
        }
        return false;
    }


    /**
     * Creates a deep copy of the input array and returns it
     */
    private static char[][] copyOf(char[][] A) {
        char[][] B = new char[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    // Time: O(n*n*something else like validation?) b/c there are n rows and n columns to try for each row
    public static List<char[][]> nQueensSolutions(int n) {
        List<char[][]> answers = new ArrayList<>();

        int queenCols[] = new int[n];
        for (int i = 0; i < n; i++) {
            queenCols[i] = 0;
        }

        nQueensHelper(new char[n][n], 0, queenCols, answers);
        return answers;
    }

    /**
     * Helper for nQueensSolutions. Given a graph, perform DFS and find all solutions for the board
     * @param current Current game board
     * @param row The row to initialize on this recursive stack call
     * @param queenCols Columns that currently have a queen
     * @param solutions Holds all the solutions to the problem
     * time: O(n! * n^2)
     * space: O(n^2)
     */
    private static void nQueensHelper(char[][] current, int row, int[] queenCols, List<char[][]> solutions) {
        // Base case: All rows are filled
        if (row >= current.length) {
            solutions.add(copyOf(current));
            return;
        }

        // Iterate every choice of the position of the queen in a row of the game board
        for (int i = 0; i < current.length; i++) {
            // Fill in row
            current[row] = new char[current.length];
            for (int j = 0; j < current[i].length; j++) {
                if (i == j) {
                    current[row][j] = 'Q';
                } else {
                    current[row][j] = '.';
                }
            }

            // Prune branch from search space if board is no longer valid
            // O(n^2)
            if (queenCols[i] != 0 || checkDiagonal(current, row, i)) {
                current[row] = new char[current.length];
                continue;
            }

            // Column i is now used
            queenCols[i] = 1;

            nQueensHelper(current, row + 1, queenCols, solutions);

            // Reset board
            current[row] = new char[current.length];
            queenCols[i] = 0;
        }
    }

}
