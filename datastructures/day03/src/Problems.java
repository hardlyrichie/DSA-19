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

    // Time: O(k + n) = O(n)
    // Adds elements of A into result list and checks if new element is less than last elements in list, removing k elements
    public static List<Integer> removeKDigits(int[] A, int k) {
        List<Integer> l = new ArrayList<Integer>();

        for (int i = 0; i < A.length; i++) {
            if (l.size() > 0) {
                boolean checkRemove = true;
                while (k > 0 && checkRemove && l.size() - 1 >= 0) { // ask about runtime. O(k+n)? or O(n^2)
                    if (A[i] < l.get(l.size() - 1)) { // Check last element in l, if greater than current element, remove
                        l.remove(l.size() - 1);
                        k--;
                    } else {
                        checkRemove = false;
                    }
                }
            }
            l.add(A[i]);
        }

        // Remove from end if need be to clear k
        for (int i = 0; i < k; i++) {
            l.remove(l.size() - 1);
        }

        return l;
    }

    // Time: O(n), Space: O(1)
    // Finds midpoint of singly linked list. Breaks list into two, reversing the second half. Then compares the two lists starting
    // from each list's head node and going inwards. -> -> -> <- <- <-
    // Odd palindromes are turned even by skipping over the middle node.
    public static boolean isPalindrome(Node n) {
        if (n == null || n.next == null) { // empty or single list
            return true;
        }

        // slowPtr arrives at half way of linkedlist as fastPtr moves 2x speed
        Node slowPtr = n, fastPtr = n, prevSlowPtr = n;

        while (fastPtr != null && fastPtr.next != null) {
            fastPtr = fastPtr.next.next; // 2x speed
            prevSlowPtr = slowPtr;
            slowPtr = slowPtr.next; // 1x speed
        }

        // Odd palindrome, move slowPtr over middle node
        if (fastPtr != null) {
            slowPtr = slowPtr.next;
        }

        // Split list in half and reverse the second half
        Node secondHalf = slowPtr;
        prevSlowPtr.next = null; // Splits list in half

        // Reverse
        Node prev = null;
        Node current = secondHalf;
        Node next;
        while (current != null)
        {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        secondHalf = prev; // secondHalf now starts at tail node or original list

        // Compare the two lists
        Node forwards = n;
        Node backwards = secondHalf;

        while (forwards != null && backwards != null) {
            if (forwards.val == backwards.val) {
                forwards = forwards.next;
                backwards = backwards.next;
            } else {
                return false;
            }
        }

        if (forwards == null && backwards == null) {
            return true;
        } else { // when one is null and the other isn't causing the exit of the while loop
            return false;
        }
    }

    // Time: O(n), Space: O(n)
    // 2 stacks or 1 stack and one stringbuilder. Stack holds the operators and numbers are appended to the string.
    // If encounters a ')', signifying end of parenthesis block, pop off stack till reach '(' and append operators into string.
    // Then remove the parentheses.
    public static String infixToPostfix(String s) {
        Stack<Character> op = new Stack<Character>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+': case '-': case '*': case '/':
                    if (op.size() > 0 && op.peek() != '(') {
                        postfix.append(op.pop() + " ");
                    }
                    op.push(c);
                    break;
                case '(':
                    op.push(c);
                    break;
                case ' ':
                    continue;
                case ')':
                    while (!op.isEmpty() && op.peek() != '(') // Remove all operators until '('
                        postfix.append(op.pop() + " ");
                    if (!op.isEmpty() && op.peek() != '(') { // Next element in op should be a '(', if not error
                        return "Invalid Expression";
                    } else {
                      op.pop(); // Remove the '('
                    }
                    break;
                default:
                    if (Character.isDigit(c)) {
                        postfix.append(c + " ");
                    } else {
                        return "Unrecognized term";
                    }
            }
        }

        while (op.size() > 0) {
            postfix.append(op.pop() + " ");
        }

       return postfix.toString().trim();

    }

}
