import java.lang.reflect.Array;
import java.util.*;

public class TripleSum {

    static int tripleSum(int arr[], int sum) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            int addend = sum - arr[i];
            count += pairSum(arr, addend, i + 1);
        }
        return count;
    }

    static int pairSum(int arr[], int sum, int start) {
        HashMap<Integer, Integer> pairs = new HashMap<>();
        for (int i = start; i < arr.length; i++) {
            int addend = sum - arr[i];

            if (pairs.containsKey(addend)) {
                pairs.put(addend, arr[i]);
            }
            pairs.put(arr[i], null);
        }

        // Gets number of nonnull keys. This is the number of pairs that add up to sum.
        int count = 0;
        for (Map.Entry mapElement : pairs.entrySet()) {
            Integer value = (Integer)mapElement.getValue();
            if (value != null) {
                count++;
            }
        }

        return count;
    }
}
