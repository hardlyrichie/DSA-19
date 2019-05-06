public class EditDistance {
    /*
    * 5 Steps of Dynamic Programming:
    * 1. Subproblems: DP[i][j] = edit distance from suffix a[i:] to suffix b[j:]
    * 2. Guess: Which of three operations to perform (insert, delete, replace)
    * 3. Recurrence Relation: minimum edit distance
    * 4. Recurse/Memoize: DP[i][j] = 1 + min(DP[i][j+1], DP[i+1][j], DP[i+1][j+1])
    * 5. Solve original problem: DP[0,0]
    *
    * Time Complexity: len(a)*len(b) subproblems, O(3) per subproblem -> O(3ab)
    */
    public static int minEditDist(String a, String b) {
        int[][] DP = new int[a.length()+1][b.length()+1];

        // Bottom up: build DP table by increasing suffix sizes
        for (int i = a.length(); i >= 0; i--) {
            for (int j = b.length(); j >= 0; j--) {
                // Base Case
                if (i == a.length() && j == b.length()) {
                    DP[i][j] = 0;
                } else if (i == a.length()) { // To go from empty string, need to insert [j:] characters
                    DP[i][j] = b.length() - j;
                } else if (j == b.length()) { // Need to remove [i:] characters
                    DP[i][j] = a.length() - i;
                } else if (a.charAt(i) == b.charAt(j)) { // Same character, edit distance is the distance for suffix [i+1:] -> [j+1:]
                    DP[i][j] = DP[i+1][j+1];
                } else {
                    DP[i][j] = 1 + min(DP[i][j+1], DP[i+1][j], DP[i+1][j+1]); // Choose whether to insert, delete or replace
                }
            }
        }

        return DP[0][0];
    }

    private static int min(int a, int b, int c){
        if (a < b && a < c) {
            return a;
        } else if (b < a && b < c) {
            return b;
        } else {
            return  c;
        }
    }

}