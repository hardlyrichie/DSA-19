import java.util.Arrays;
import java.util.HashMap;

public class LongestIncreasingSubsequence {
    /*
     * 5 DP Steps
     * Subproblem:
     * DP[i] = length of the longest subsequence starting at A[i]
     * Guess:
     * Guess which of the next subsequences is longest
     * Recurrence Relation:
     * DP[i] = DP[i+1] + 1, as long as the next value (i+1) is greater that the current value (i), sequence is increasing
     * Recurse/Memoize:
     * Recurse back to find length of DP[i]
     * Solve Original:
     * return max(DP)
     */

    // Runtime: O(n^2)
    // Space: O(n)
    public static int LIS(int[] A) {
        int[] DP = new int[A.length];

        int answer = 0;
        // Find which subsequence is longest (testing subsequences starting at i)
        for (int i = 0; i < A.length; i++) {
            answer = Math.max(recurse(A, DP, i), answer);
        }

        return answer;
    }

    // DP[i] is the length of the LIS whose first element is A[i]
    private static int recurse(int[] A, int[] DP, int i) {
        // Have we already solved this subproblem
        if (DP[i] != 0) return DP[i];

        int answer = 1;
        for (int j = i + 1; j < A.length; j++) {
            // checks whether next number in sequence is an increase. If so find that subsequence's length. Skips (remove) values less than
            if (A[j] > A[i]) {
                answer = Math.max(answer, recurse(A, DP, j) + 1);
            }
        }

        // Store our answer and return it
        DP[i] = answer;

        return DP[i];
    }
}