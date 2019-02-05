package your_code;
import ADTs.StackADT;

import java.util.LinkedList;

/**
 * An implementation of the Stack interface.
 */
public class MyStack implements StackADT<Integer> {

    private LinkedList<Integer> ll;
    private LinkedList<Integer> max;

    public MyStack() {
        ll = new LinkedList<>();
        max = new LinkedList<>();
    }

    @Override
    public void push(Integer e) {
        // Stacks implemented with linkedlists add to beginning so that push and pop is O(1)
        ll.addFirst(e);
        // Add to max list if e is a new biggest int
        if (max.size() == 0 || e > max.getFirst()) {
            max.addFirst(e);
        }
    }

    @Override
    public Integer pop() {
        // Remove from max list if element from pop is in max list
        if (max.getFirst() == ll.getFirst()) {
            max.removeFirst();
        }

        Integer pop = ll.removeFirst();
        return pop;
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public Integer peek() {
        return ll.getFirst();
    }

    public Integer maxElement() {
        return max.getFirst();
    }
}
