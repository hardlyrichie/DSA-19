import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Permutations {

    // Time: O(n!)
    // Space: O(n)
    public static List<List<Integer>> permutations(List<Integer> A) {
        List<List<Integer>> permutations = new LinkedList<>();
        List<Integer> unused = new LinkedList<>();
        for (Integer n : A) {
            unused.add(n);
        }
        permutationsHelper(new LinkedList<Integer>(), unused, permutations);

        return permutations;
    }

    private static void permutationsHelper(List<Integer> current, List<Integer> unused, List<List<Integer>> permutations) {
        // Base case
        if (unused.size() == 0) {
            permutations.add(new ArrayList<Integer>(current));
            return;
        }

        // Iterate every choice of integer
        for (Integer n : new ArrayList<Integer>(unused)) {
            current.add(n);
            unused.remove(n);
            permutationsHelper(current, unused, permutations);
            current.remove(n);
            unused.add(n);
        }
    }
}
