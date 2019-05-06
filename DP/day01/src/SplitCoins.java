import java.util.Arrays;

public class SplitCoins {
    /*
     * 5 Steps of Dynamic Programming:
     * 1. Subproblems: DP[i][j] = optimal final sum of left pile that is closest to total sum / 2 for coins up to i from [j:].
     * Minimizes distance left pile sum is from the total sum / 2 as if left sum = total sum / 2, then left and right
     * piles will have equal sums. j keeps track of current left sum.
     * 2. Guess: Whether to add coin i to left or right pile
     * 3. Recurrence Relation: minimum distance between adding coin to left or right pile
     * 4. Recurse/Memoize: DP[i][j] = min_dist(DP[i+1][j], DP[i+1][j+coins[i]])
     * 5. Solve original problem: Math.abs(DP[0,0] - (total_sum - DP[0,0]))
     *
     * Time Complexity: coins X sum subproblems, O(2) per subproblem -> O(2CS)
     */
    public static int splitCoins(int[] coins) {
        int sum = 0;
        for (int c : coins) {
            sum += c;
        }

        // Initialize DP to -1
        int DP[][] = new int[coins.length][sum + 1];
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j <= sum; j++) {
                DP[i][j] = -1;
            }
        }

        // Calculate difference between piles
        int leftPile = recurs(0, 0, sum, coins, DP);
        int rightPile = sum - leftPile;
        return Math.abs(leftPile - rightPile);
    }

    private static int recurs(int i, int j, int sum, int[] coins, int[][] DP) {
        // Base case, all coins used. Return sum of left pile
        if (i == coins.length) {
            return j;
        }

        if (DP[i][j] != -1) return DP[i][j];

        int placedCoin = recurs(i+1,j+coins[i], sum, coins, DP);
        int skippedCoin = recurs(i+1,j, sum, coins, DP);

        int optimalSum = skippedCoin;
        double mid = sum / 2.0;
        if (Math.abs(placedCoin - mid) < Math.abs(skippedCoin - mid)) {
            optimalSum = placedCoin;
        }

        DP[i][j] = optimalSum;
        return DP[i][j];
    }
}
