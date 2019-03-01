import java.util.LinkedList;

public class Problems {

    static void sortNumsBetween100s(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] += 100;
        }
        CountingSort.countingSort(A);
        for (int i = 0; i < A.length; i++) {
            A[i] -= 100;
        }
    }

    /**
     * @param n the character number, 0 is the rightmost character
     * @return
     */
    private static int getNthCharacter(String s, int n) {
        return s.charAt(s.length() - 1 - n) - 'a';
    }


    /**
     * Use counting sort to sort the String array according to a character
     *
     * @param n The digit number (where 0 is the least significant digit)
     */
    static void countingSortByCharacter(String[] A, int n) {
        LinkedList<String>[] L = new LinkedList[26]; // The number of letters in alphabets
        for (int i = 0; i < L.length; i++)
            L[i] = new LinkedList<>();
        for (String i : A) {
            L[getNthCharacter(i, n)].add(i);
        }
        int j = 0;
        // O(b + n)
        for (LinkedList<String> list : L) {
            while (list.size() > 0) {
                A[j] = list.remove();
                j++;
            }
        }
    }

    /**
     * @param stringLength The length of each of the strings in S
     */
    static void sortStrings(String[] S, int stringLength) {
        for (int i = 0; i < S[0].length(); i++) {
            countingSortByCharacter(S, i);
        }
    }

    /**
     * @param A The array to count swaps in
     */

    public static int countSwaps(int[] A) {
        // TODO
        return 0;
    }

}
