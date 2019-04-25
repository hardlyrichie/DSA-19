import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BarnRepair {
    public static int solve(int M, int[] occupied) {
        Arrays.sort(occupied);

        // Priority queue where large numbers have high priority
        PriorityQueue<Integer> spaces = new PriorityQueue<Integer>(Comparator.reverseOrder());


        // Determine number of gaps (empty stalls) between stalls with cows
        int gaps = 0;
        for (int i = 1; i < occupied.length; i++) {
            // Empty stalls exist
            if (occupied[i] - occupied[i-1] > 1) {
                gaps++;
            }
        }

        // Fill priority queue with gaps - M + 1 (number of gaps to cover with a board) number of smallest sized gaps
        for (int i = 1; i < occupied.length; i++) {
            if (occupied[i] - occupied[i-1] > 1) {
                spaces.add(occupied[i] - occupied[i-1] - 1);

                // Remove the largest sized gap if too many are in priority queue
                if (spaces.size() > gaps - (M - 1)) spaces.poll();
            }
        }

        // stalls will have at least occupied number of stalls to cover
        int stalls = occupied.length;

        // Add number of stalls within the smallest gaps to the stall count
        int size = spaces.size();
        for (int i = 0; i < size; i++) {
            // Greedy choice: smallest gaps
            stalls += spaces.poll();
        }

        return stalls;

    }
}