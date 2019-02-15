
public class    InsertionSort extends SortAlgorithm {
    /**
     * Use the insertion sort algorithm to sort the array
     *
     * TODO
     * Best-case runtime: O(n)
     * Worst-case runtime: O(n^2)
     * Average-case runtime: O(n^2)
     *
     * Space-complexity: O(n), O(1) if modify array passed into method
     */
    @Override
    public int[] sort(int[] array) {
        int[] sorted = array.clone();

        for (int i = 1; i < sorted.length; i++) {
            int j = i;
            // Checks left neighbor if it is greater than sorted[i]
            while (j - 1 >= 0 && sorted[j] < sorted[j-1]) {
                swap(sorted,j, j-1);
                j--;
            }
        }

        return sorted;
    }
}
