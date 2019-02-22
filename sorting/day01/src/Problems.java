import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Problems {

    private static PriorityQueue<Integer> minPQ() {
        return new PriorityQueue<>(11);
    }

    private static PriorityQueue<Integer> maxPQ() {
        return new PriorityQueue<>(11, Collections.reverseOrder());
    }

    private static double getMedian(List<Integer> A) {
        double median = (double) A.get(A.size() / 2);
        if (A.size() % 2 == 0)
            median = (median + A.get(A.size() / 2 - 1)) / 2.0;
        return median;
    }

    // Runtime of this algorithm is O(N^2). Sad! We provide it here for testing purposes
    public static double[] runningMedianReallySlow(int[] A) {
        double[] out = new double[A.length];
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            int j = 0;
            while (j < seen.size() && seen.get(j) < A[i])
                j++;
            seen.add(j, A[i]);
            out[i] = getMedian(seen);
        }
        return out;
    }


    /**
     *
     * @param inputStream an input stream of integers
     * @return the median of the stream, after each element has been added
     *
     * Keeps track of the number before (or at) median and number after median. Calculates median using those values.
     */
    public static double[] runningMedian(int[] inputStream) {
        double[] runningMedian = new double[inputStream.length];
        // Keeps track of numbers before median and the median (when array is odd)
        PriorityQueue<Integer> before = maxPQ();
        // Keeps track of numbers after the median
        PriorityQueue<Integer> after = minPQ();

        for (int i = 0; i < inputStream.length; i++) {
            int n = inputStream[i];
            // First element
            if (before.size() == 0 && after.size() == 0) {
                before.offer(n);
                runningMedian[i] = n;
                continue;
            }
            if (n <= before.peek()) {
                if (i % 2 != 0) { // even median
                    // Move over largest element in before to balance out queues
                    after.offer(before.poll());
                    before.offer(n);
                    runningMedian[i] = (before.peek() + after.peek()) / 2.0;
                } else {
                    before.offer(n);
                    runningMedian[i] = before.peek();
                }
            } else {
                after.offer(n);
                if (i % 2 != 0) { // even median
                    runningMedian[i] = (before.peek() + after.peek()) / 2.0;
                } else {
                    // Move median to before queue to balance out.
                    before.offer(after.poll());
                    runningMedian[i] = before.peek();
                }
            }
        }

        return runningMedian;
    }

}
