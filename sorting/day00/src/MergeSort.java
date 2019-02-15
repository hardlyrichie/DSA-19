import java.util.Arrays;

public class MergeSort extends SortAlgorithm {

    private static final int INSERTION_THRESHOLD = 10;

    /**
     * This is the recursive step in which you split the array up into
     * a left and a right portion, sort them, and then merge them together.
     * Use Insertion Sort if the length of the array is <= INSERTION_THRESHOLD
     *
     * TODO
     * Best-case runtime: O(nlgn)
     * Worst-case runtime: O(nlgn)
     * Average-case runtime: O(nlgn)
     *
     * Takes O(log_2 n) to split arrays
     * Takes O(n) to merge arrays
     *
     * Space-complexity: O(n)
     */
    @Override
    public int[] sort(int[] array) {
        // Base case
        if (array.length <= INSERTION_THRESHOLD) {
            return new InsertionSort().sort(array);
        }

        int middle = array.length / 2;
        int[] left = sort(Arrays.copyOfRange(array, 0, middle));
        int[] right = sort(Arrays.copyOfRange(array, middle, array.length));

        return merge(left, right);
    }

    /**
     * Given two sorted arrays a and b, return a new sorted array containing
     * all elements in a and b. A test for this method is provided in `SortTest.java`
     */
    public int[] merge(int[] a, int[] b) {
        int[] sorted = new int[a.length + b.length];
        int count = 0, a_index = 0, b_index = 0;
        while (count < a.length + b.length) {
            // If b list is all merged and a still has elements, or if element at a is less element at b
            if (b_index >= b.length || (a_index < a.length && a[a_index] < b[b_index])) {
                sorted[count] = a[a_index];
                a_index++;
            } else {
                sorted[count] = b[b_index];
                b_index++;
            }
            count++;
        }
        return sorted;
    }

}
