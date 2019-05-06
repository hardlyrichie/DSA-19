import java.util.ArrayList;
import java.util.Arrays;

public class BalloonPopping {
    /*
     * 5 Steps of Dynamic Programming:
     * 1. Subproblems: DP[i][j] = max value from popping substring balloons B[i:j]
     * 2. Guess: where's last balloon to pop
     * 3. Recurrence Relation: maximize value
     * 4. Recurse/Memoize: DP[i] = B[i-1] * B[k] * B[j+1] + recurse(i, k-1, DP, B) + recurse(k+1, j, DP, B), split
     * into left and right subproblems
     * 5. Solve original problem: DP[0][B.length]
     * Note: above indexes are slightly off b/c in implementation i used a padded B, so everything is offset
     *
     * Time Complexity: n^2 subproblems, O(n) per subproblem -> O(n^3)
     */
    public static int maxPoints(int[] B) {
        // Pad B with 1 in start and end
        int[] paddedB = new int[B.length + 2];
        for (int i = 0; i < B.length; i++) {
            paddedB[i+1] = B[i];
        }
        paddedB[0] = 1;
        paddedB[paddedB.length - 1] = 1;

        // Initialize DP table
        int[][] DP = new int[paddedB.length][paddedB.length];

        // Max value from popping B[0:end] or paddedB[1:paddedB.length-2]
        return recurse(1, paddedB.length - 2, DP, paddedB);
    }

    private static int recurse(int i, int j, int[][] DP, int[] B) {
        // Base case
        if (i > j || i > B.length - 2 || j < 1) return 0;

        if (DP[i][j] != 0) return DP[i][j];

        // Optimize, find maximum value from all subproblems
        int max = -1;
        for (int k = i; k <= j; k++) {
            // Guess last balloon k, and recurse into left and right subproblems (motivation behind is that bursting in
            // the middle causes adjacent balloons to collapse in, so have to check left and right problems going backwards)
            int value = B[i-1] * B[k] * B[j+1] + recurse(i, k-1, DP, B) + recurse(k+1, j, DP, B);
            if (value > max) {
                max = value;
            }
        }

        DP[i][j] = max;
        return DP[i][j];
    }
}
