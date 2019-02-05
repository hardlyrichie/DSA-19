package your_code;

import java.text.Collator;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * An implementation of a priority Queue
 */
public class MyPriorityQueue {
    private LinkedList<Integer> ll;

    public MyPriorityQueue() {
        ll = new LinkedList<Integer>();
    }

    // O(n)
    public void enqueue(int item) {
        ll.add(item);
        ll.sort((a, b) -> a < b ? 1 : -1);
    }

    /**
     * Return and remove the largest item on the queue.
     * O(1)
     */
    public int dequeueMax() {
       return ll.removeFirst();
    }
}