import java.util.ArrayList;
import java.util.List;

public class CoinsOnAClock {

    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        List<char[]> result = new ArrayList<>();

        ArrayList<Character> coinsLeft = new ArrayList<>();
        for (int i = 0; i < pennies; i++) {
            coinsLeft.add('p');
        }
        for (int i = 0; i < nickels; i++) {
            coinsLeft.add('n');
        }
        for (int i = 0; i < dimes; i++) {
            coinsLeft.add('d');
        }

        coinsOnAClockHelper(new char[hoursInDay], coinsLeft, 0, result);
        return result;
    }

    /**
     * Helper for coinsOnAClock. Given a graph, perform DFS and find all solutions for the clock
     * @param current Current clock
     * @param coinsLeft List keeping track of how many of which coins left to place
     * @param pos Current position on clock
     * @param solutions Holds all the solutions to the problem
     * time: O(3^n)
     * space: O(n)
     */
    private static void coinsOnAClockHelper(char[] current, ArrayList<Character> coinsLeft, int pos, List<char[]> solutions) {
        // Base case: No coins left
        if (coinsLeft.size() == 0) {
            solutions.add(current.clone());
            return;
        }

        // Iterate every choice of coin that is left
        for (int i = 0; i < coinsLeft.size(); i++) {
            // Skip redundant coin selection
            if (i - 1 >= 0 && coinsLeft.get(i) == coinsLeft.get(i - 1)){
                continue;
            }

            // Prune branch from search space if coin at hour already exists or no coin
            if (current[pos] == 'p' || current[pos] == 'n' || current[pos] == 'd') {
                return;
            }

            current[pos] = coinsLeft.remove(i);

            int oldPos = pos;
            switch (current[pos]) {
                case 'p':
                    pos += 1;
                    break;
                case 'n':
                    pos += 5;
                    break;
                case 'd':
                    pos += 10;
                    break;
                default:
                    break;
            }

            // If pos is greater than hoursInDay, wrap pos around
            pos = pos % current.length;

            coinsOnAClockHelper(current, new ArrayList<Character>(coinsLeft), pos, solutions);

            // Reset values
            coinsLeft.add(i, current[oldPos]);
            current[oldPos] = '\u0000';
            pos = oldPos;
        }
    }
}
