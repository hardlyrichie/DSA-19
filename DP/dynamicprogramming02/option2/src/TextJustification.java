import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TextJustification {

    private static double cost(String[] words, int lo, int hi, int m) {
        if (hi <= lo)
            throw new IllegalArgumentException("Hi must be higher than Lo");
        int length = hi-lo-1; // account for spaces;
        for (int i = lo; i < hi; i++) {
            length += words[i].length();
        }
        if (length > m)
            return Double.POSITIVE_INFINITY;
        return Math.pow(m-length, 3);
    }

    /*
     * 5 Steps of Dynamic Programming:
     * 1. Subproblems: DP[i] = minimum cost for text starting at word i, suffix [i:]
     * 2. Guess: where to end current line, index j
     * 3. Recurrence Relation: minimize cost
     * 4. Recurse/Memoize: DP[i] = cost(w, i, j, m) + recurs(w, j, m, DP, nextBreak) for j in range(i+1,w.length)
     * 5. Solve original problem: create list of starting indices for each line
     *
     * Time Complexity: n subproblems, O(n) per subproblem -> O(n^2)
     */
    public static List<Integer> justifyText(String[] w, int m) {
        double[] DP = new double[w.length + 1];
        for (int i = 0; i < DP.length; i++) {
            DP[i] = -1;
        }

        // Holds the index j for every index i such that w[i:j] is the minimum cost for that i index
        int[] nextBreak = new int[w.length];

        recurs(w, 0, m, DP, nextBreak);

        // Build list of indices of the first word of each line of justified text
        ArrayList<Integer> idxFirstWords = new ArrayList<>();
        int i = 0;
        while (i < w.length) {
            idxFirstWords.add(i);
            i = nextBreak[i];
        }
        return idxFirstWords;

    }

    private static double recurs(String[] w, int i, int m, double[] DP, int[] nextBreak) {
        // Base case
        if (i == w.length) return 0;

        if (DP[i] != -1) return DP[i];

        // Find minimum cost for suffix [i:]
        double minCost = Double.MAX_VALUE;
        for (int j = i + 1; j <= w.length; j++) {
            double c = cost(w, i, j, m) + recurs(w, j, m, DP, nextBreak);
            if (c < minCost) {
                minCost = c;
                nextBreak[i] = j;
            }
        }

        DP[i] = minCost;
        return DP[i];
    }

}