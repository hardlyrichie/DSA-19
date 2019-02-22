public class HeapSort extends SortAlgorithm {
    int size;
    int[] heap;

    private int parent(int i) {
        return (i-1) / 2;
    }

    private int leftChild(int i) {
        return 2*i + 1;
    }

    private int rightChild(int i) {
        return 2 * (i + 1);
    }

    // Check children, and swap with larger child if necessary.
    // Corrects the position of element indexed i by sinking it.
    // Use either recursion or a loop to then sink the child
    public void sink(int i) {
        // Don't call sink on leaves b/c nothing to swap with
        if (leftChild(i) >= size) {
            return;
        }

        int maxChild = rightChild(i) >= size ? leftChild(i) : max(leftChild(i), rightChild(i));

        if (heap[i] < heap[maxChild]) {
            swap(heap, i, maxChild);
            sink(maxChild);
        }
    }

    private int max(int left, int right) {
        return heap[left] >= heap[right] ? left : right;
    }

    // Given the array, build a heap by correcting every non-leaf's position, starting from the bottom, then
    // progressing upward
    // Does this by calling sink on every non-leaf
    public void heapify(int[] array) {
        this.heap = array;
        this.size = array.length;

        // Starting at first non-leaf given at index Math.floor((n/2)-1)
        for (int i=this.size / 2 - 1; i>=0; i--) {
            sink(i);
        }
    }

    /**
     * Best-case runtime: O(nlogn)
     * Worst-case runtime: O(nlogn)
     * Average-case runtime: O(nlogn)
     *
     * Space-complexity: O(1)
     */
    @Override
    public int[] sort(int[] array) {
        heapify(array);

        for (int i=size-1; i>0; i--) {
            swap(heap, 0, i);
            size--;
            sink(0);
        }

        return heap;
    }
}
