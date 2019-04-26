public class DiceRollSum {
    /*
    * 5 DP Steps
    * Subproblem:
    * DP[i] = # of 6 sided dice roll sequences that sum up to i
    * Guess:
    * Guess which sequences of dice sum up to i
    * Recurrence Relation:
    * DP[i] = diceRoll(i-1) + diceRoll(i-2) + diceRoll(i-3) + ... + diceRoll(i-6)
    * Recurse/Memoize:
    * Recurse back to sum N
    * Solve Original:
    * return DP[n]
    */

    // Runtime: O(6n) = O(n)
    // Space: O(N+1) = O(N)
    public static int diceRollSum(int N) {
        int[] DP = new int[N + 1];

        // Store base case in memo
        DP[0] = 1;

        // Solve subproblems
        return diceRollSumRecurs(N, DP);
    }

    private static int diceRollSumRecurs(int i, int[] DP) {
        // Have we already solved this subproblem
        if (DP[i] != 0) return DP[i];

        // 6 possible subproblems as one dice can only be values 1 - 6
        int j = i > 6 ? 6 : i;

        // Solve subproblems
        int answer = 0;
        for (int k = 1; k <= j; k++) {
            answer += diceRollSumRecurs(i - k, DP);
        }

        // store our answer and return it
        DP[i] = answer;

        return answer;
    }

}
