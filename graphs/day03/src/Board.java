import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Board definition for the 8 Puzzle challenge
 */
public class Board {

    private int n;
    public int[][] tiles;

    // Create a 2D array representing the solved board state
    private int[][] goal = {{1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 0}};

    private int emptyRow, emptyCol;

    /*
     * Set the global board size and tile state
     */
    public Board(int[][] b) {
        tiles = new int[b.length][b[0].length];

        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < b.length; j++){
                tiles[i][j] = b[i][j];

                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

        n = size();
    }

    /*
     * Size of the board 
     (equal to 3 for 8 puzzle, 4 for 15 puzzle, 5 for 24 puzzle, etc)
     */
    private int size() {
        return tiles.length;
    }

    /*
     * Sum of the manhattan distances between the tiles and the goal, not including the empty position
     */
    public int manhattan() {
        int sum = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                sum += dist(i, j);
            }
        }
        return sum;
    }

    private int dist(int r, int c){
        int val = tiles[r][c];

        if(val == 0) return 0;

        val--;
        return Math.abs(val / n - r) + Math.abs(val % n - c);
    }

    /*
     * Compare the current state to the goal state
     */
    public boolean isGoal() {
        return manhattan() == 0;
    }

    /*
     * Returns true if the board is solvable
     * Board is solvable if it has even number of inversions: any pair of blocks i and j where i < j but i appears after j
     */
    public boolean solvable() {
        int inv = 0;
        for (int i = 0; i < n*n; i++) {
            int iIndex[] = countToIndex(i);
            int iRow = iIndex[0], iCol = iIndex[1];

            if (tiles[iRow][iCol] == 0) continue;

            for (int j = i + 1; j < n*n; j++) {
                int jIndex[] = countToIndex(j);
                int jRow = jIndex[0], jCol = jIndex[1];

                if (tiles[jRow][jCol] != 0 && tiles[iRow][iCol] > tiles[jRow][jCol]) {
                    inv++;
                }
            }
        }

        return inv % 2 == 0;
    }

    private int[] countToIndex(int count) {
        // Convert count to 2D index
        int col = count % 3;
        int row = (count - col) / 3;
        return new int[] {row, col};
    }

    /*
     * Return all neighboring boards in the state tree
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        // Slide top tile into empty space
        if (emptyRow > 0) {
            slideVertically(emptyRow, emptyCol, -1, neighbors);
        }
        // Slide bottom tile into empty space
        if (emptyRow < n - 1) {
            slideVertically(emptyRow, emptyCol, 1, neighbors);
        }
        // Slide left tile into empty space
        if (emptyCol > 0) {
            slideHorizontally(emptyRow, emptyCol, -1, neighbors);
        }
        // Slide right tile into empty space
        if (emptyCol < n - 1) {
            slideHorizontally(emptyRow, emptyCol, 1, neighbors);
        }
        return neighbors;
    }

    private void slideVertically(int i, int j, int tile, List<Board> neighbors) {
        int temp = tiles[i+tile][j];
        tiles[i+tile][j] = 0;
        tiles[i][j] = temp;
        neighbors.add(new Board(tiles));

        // Undo slide
        tiles[i][j] = 0;
        tiles[i+tile][j] = temp;
    }

    private void slideHorizontally(int i, int j, int tile, List<Board> neighbors) {
        int temp = tiles[i][j+tile];
        tiles[i][j+tile] = 0;
        tiles[i][j] = temp;
        neighbors.add(new Board(tiles));

        // Undo slide
        tiles[i][j] = 0;
        tiles[i][j+tile] = temp;
    }

    /*
     * Check if this board equals a given board state
     */
    @Override
    public boolean equals(Object x) {
        // Check if the board equals an input Board object
        if (x == this) return true;
        if (x == null) return false;
        if (!(x instanceof Board)) return false;
        // Check if the same size
        Board y = (Board) x;
        if (y.tiles.length != n || y.tiles[0].length != n) {
            return false;
        }
        // Check if the same tile configuration
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != y.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        String result = "--------\n";
        for (int i = 0; i < tiles.length; i++) {
            result += "|";
            for (int j = 0; j < tiles[i].length; j++) {
                result += tiles[i][j] + " ";
            }
            result += "|\n";
        }
        result += "--------\n";
        return result;
    }


    public static void main(String[] args) {
        // DEBUG - Your solution can include whatever output you find useful
        int[][] initState = {{8, 1, 2},{0,4,3},{7,6,5}};
        Board board = new Board(initState);

        System.out.println("Board:");
        System.out.println(board);

        System.out.println("Size: " + board.size());
        System.out.println("Solvable: " + board.solvable());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Neighbors:");
        Iterable<Board> it = board.neighbors();
        for (Board b : it) {
            System.out.println(b);
        }
    }
}
