public class CountingSort {

    /**
     * Use counting sort to sort non-negative integer array A.
     * Runtime: O(n + k)
     *
     *
     * k: maximum element in array A
     */
    static void countingSort(int[] A) {
        // O(n)
        int k = findMax(A);
        int[] counts = new int[k+1];

        // O(n)
        for (int e : A) {
            counts[e]++;
        }

        // O(n + k)
        int i = 0;
        for (int j = 0; j < k+1; j++) {
            while (counts[j] > 0) {
                A[i] = j;
                counts[j]--;
                i++;
            }
        }
    }

    static int findMax(int[] A) {
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            max = A[i] > max ? A[i] : max;
        }
        return max;
    }

}
