import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Problems {

    // O(n)
    public static int leastSum(int[] A) {
        HashMap<Integer, Integer> AHash = new HashMap<>();

        // O(n), puts counts of digits into a hashmap
        for (int i : A) {
            if (AHash.get(i) != null) {
                AHash.put(i, AHash.get(i) + 1);
            } else {
                AHash.put(i, 1);
            }
        }

        Stack<Integer> digits1 = new Stack<Integer>();
        Stack<Integer> digits2 = new Stack<Integer>();

        // O(n)
        int count = 0;
        for (int i = 0; i < 10; i++) {
            if (!AHash.containsKey(i)) continue;

            for (int j = 0; j < AHash.get(i); j++) {
                if (count % 2 == 0) {
                    digits1.push(i);
                } else {
                    digits2.push(i);
                }
                count++;
            }
        }

        // O(n/2)
        int n1 = 0, size = digits1.size();
        for (int i = 0; i < size; i++) {
            int digit = digits1.pop();

            if (digit == 0) continue;

            n1 += digit * (Math.pow(10, i));
        }

        // O(n/2)
        int n2 = 0;
        size = digits2.size();
        for (int i = 0; i < size; i++) {
            int digit = digits2.pop();

            if (digit == 0) continue;

            n2 += digit * (Math.pow(10, i));
        }

        return n1 + n2;
    }
}
