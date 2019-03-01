import java.util.ArrayList;
import java.util.List;

public class RadioTowers {
    static class Tower {
        double x, y;
        Tower(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double getDist(Tower t1, Tower t2) {
        double xDiff = t1.x - t2.x;
        double yDiff = t1.y - t2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private static boolean isWithin(Tower t1, Tower t2, int dist) {
        return getDist(t1, t2) <= dist;
    }

    // O(n)
    // Strip contains a list of Towers sorted by x-coordinate, whose y-coordinates all fall in a 2-mile strip
    // Return true if no two towers are within 1 mile
    public static boolean checkStrip(List<Tower> strip) {
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < i + 8; j++) {
                if (j < strip.size() && isWithin(strip.get(i), strip.get(j), 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // O(nlogn) b/c logn recursive calls (splitting in half) and a checkStrip call on the border O(n) in each recursive stack
    // Base case: if length 1: return true
    // Divide: Split into upper and lower
    // Conquer: check whether both upper and lower half both are valid
    // Combine: check strip on the border of two halves and see if any towers along the horizontal line are not valid
    // Return true if no two towers are within distance 1 of each other
    public static boolean validTowers(List<Tower> Lx, List<Tower> Ly) {
        if (Ly.size() == 1) {
            return true;
        }

        // Split into top & bottom towers
        List<Tower> lower = Ly.subList(0, Ly.size() / 2);
        List<Tower> upper = Ly.subList(Ly.size() / 2, Ly.size());

        if (!(validTowers(Lx, lower) && validTowers(Lx, upper))) return false;

        double stripY = Ly.get(Ly.size() / 2).y;

        // Create a list containing all towers within 1 mile of the horizontal line stripY
        List<Tower> strip = new ArrayList<Tower>();
        for (Tower tower : Lx) {
            if (Math.abs(tower.y - stripY) <= 1) {
                strip.add(tower);
            }
        }

        return checkStrip(strip);
    }
}
