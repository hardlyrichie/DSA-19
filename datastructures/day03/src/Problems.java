import javax.sound.midi.Soundbank;
import java.lang.reflect.Array;
import java.util.*;

public class Problems {

    public static class Node {
        int val;
        Node next;

        Node(int d) {
            this.val = d;
            next = null;
        }
    }

    // Add them to Linked List or stack

    // O((k+1)n) = O(n)
    public static List<Integer> removeKDigits(int[] A, int k) {
        List<Integer> l = new ArrayList<Integer>();

        for (int i : A) {
            l.add(i);
        }

        // Removes k elements from list
        for (int i = 0; i < k; i++) {
            int start = 0;

            // Search through list in segments of length k for an element to remove
            remove:
            while(true) {
                for (int j = start; j < start + (k - i); j++) {
                    // Remove item if it is greater than next item, Edge Case: Get to end of list, remove last item
                    if (j + 1 >= l.size() || l.get(j) > l.get(j+1)) {
                        l.remove(j);
                        break remove;
                    }
                }
                // Redefine start position
                start += k - i;
            }
        }

        return l;
    }

    // Reverse half the linked list

    // O(n/2) = O(n)
    public static boolean isPalindrome(Node n) {
        if (n == null) {
            return true;
        }

        Node head = n;
        Node current = head;
        Node prev = null;

        while (true) {
            if (current.next == null) {
                if (head.val == current.val) {
                    // last two nodes are equal -> even palindrome, no previous value and head is equal to current -> odd palindrome
                    if (head.next == current || prev == null) {
                        return  true;
                    }

                    // Removes first and last node (palindrome pairs)
                    prev.next = null;
                    head = head.next;
                    current = head; // start back from the top
                    prev = null;
                } else {
                    return false;
                }
            } else {
                prev = current;
                current = current.next;
            }
        }
    }

    // Two stacks

    // O(2n) = O(n)
    public static String infixToPostfix(String s) {
        Stack<Character> bank = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            bank.push(s.charAt(i));
        }

        StringBuilder postfix = new StringBuilder();
        convert:
        for (int i = 0; i < s.length(); i++) {
            char c = bank.pop();
            switch (c) {
                case '+': case '-': case '*': case '/':
                    int idx = postfix.indexOf(")");
                    postfix.replace(idx, idx+1, Character.toString(c));
                    break;
                case '(': case ' ':
                    continue convert;
                case ')':
                    postfix.insert(0, Character.toString(c) + " ");
                    break;
                default:
                    if (Character.isDigit(c)) {
                        postfix.insert(0, Character.getNumericValue(c) + " ");
                    } else {
                        return "Unrecognized term";
                    }
            }
        }

        return postfix.toString().trim();
    }

}
