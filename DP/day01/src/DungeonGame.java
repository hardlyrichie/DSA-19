public class DungeonGame {
    /*
     * 5 Steps of Dynamic Programming:
     * 1. Subproblems: DP[i][j] = minimum health needed to break even when traversing to map[i][j], prefix map location [:i][:j]
     * 2. Guess: Whether moving down or to the right leads to less health that's needed
     * 3. Recurrence Relation: minimum health required
     * 4. Recurse/Memoize: DP[i][j] = -map[i][j] + Math.min(recurs(i+1, j, DP, map), recurs(i, j+1, DP, map));
     * 5. Solve original problem: DP[0,0] + 1
     *
     * Time Complexity: MxN subproblems, O(2) per subproblem -> O(2MN)
     *
     * One direcitonal, can't do suffix (walk backwards from goal to start), b/c wouldn't be able to check DP[i][j] < 0 correctly
     */
    public static int minInitialHealth(int[][] map) {
        int[][] DP = new int[map.length][map[0].length];
        // Base case
        DP[map.length-1][map[0].length - 1] = -map[map.length-1][map[0].length - 1];

        // DP table records minimum health needed to break even. Therefore add 1 for health needed to survive
        return recurs(0, 0, DP, map) + 1;
    }

    private static int recurs(int i, int j, int[][] DP, int[][] map) {
        // Already memo-ed
        if (DP[i][j] != 0) return DP[i][j];

        // Prevent from going out of map
        if (i + 1 > map.length - 1) {
            DP[i][j] = -map[i][j] + recurs(i, j+1, DP, map);
        } else if (j + 1 > map[0].length - 1) {
            DP[i][j] = -map[i][j] + recurs(i+1, j, DP, map);
        } else {
            // Guess minimum health needed from going down or to the right
            DP[i][j] = -map[i][j] + Math.min(recurs(i+1, j, DP, map), recurs(i, j+1, DP, map));
        }

        // Prevent from thinking gaining a lot of health equates to needing no start health
        // (If h drops to 0 or below at any point, everyone died)
        if (DP[i][j] < 0) {
            DP[i][j] = Integer.MAX_VALUE;
        }

        return DP[i][j];
    }
}
