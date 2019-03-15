import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skyline {

    static class Point {
        int x, y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Building {
        private int l, r, h;
        Building(int l, int r, int h) {
            this.l = l;
            this.r = r;
            this.h = h;
        }
    }

    // O(nlogn) b/c logn recursive calls (splitting in half) and each merge takes O(n) time
    // Base case: if there is one building return list of points of length two (starting point and ending point of skyline)
    // Divide: Split into left and right skylines
    // Conquer: create new skyline from left and right skyline points where the skyline is made of the roof's of the tallest buildings
    // Combine: merge left and right skylines

    // Given an array of buildings, return a list of points representing the skyline
    public static List<Point> skyline(Building[] B) {
        if (B.length <= 1) {
            ArrayList<Point> skyline = new ArrayList<Point>();
            skyline.add(new Point(B[0].l, B[0].h));
            skyline.add(new Point(B[0].r, 0)); // End of skyline
            return skyline;
        }

        int middle = B.length / 2;
        List<Point> left = skyline(Arrays.copyOfRange(B, 0, middle));
        List<Point> right = skyline(Arrays.copyOfRange(B, middle, B.length));

        return merge(left, right);
    }

    /**
     * Given two skylines a and b, return a new appropriate skyline with no intersections.
     */
    public static List<Point> merge(List<Point> a, List<Point> b) {
        List<Point> skyline = new ArrayList<Point>();
        int i = 0, a_index = 0, b_index = 0, ha = 0, hb = 0; // ha is height of a skyline
        while (i < a.size() + b.size()) {
            // If a sublist is completely traversed, fill in skyline with the rest of b sublist
            if (a_index >= a.size()) {
                skyline.add(b.get(b_index));
                b_index++;
            } else if (b_index >= b.size()) {
                // If b sublist is completely traversed, fill in skyline with the rest of a sublist
                skyline.add(a.get(a_index));
                a_index++;
            } else if (a.get(a_index).x < b.get(b_index).x) {
                // Build skyline from a's x value and the height of the tallest building that hasn't finished (Math.max(ha,hb))
                ha = a.get(a_index).y;
                int h = Math.max(ha, hb);
                skyline.add(new Point(a.get(a_index).x, h));
                a_index++;
            } else if (a.get(a_index).x > b.get(b_index).x) {
                // Build skyline from b's x value and the height of the tallest building that hasn't finished (Math.max(ha,hb))
                hb = b.get(b_index).y;
                int h = Math.max(ha, hb);
                skyline.add(new Point(b.get(b_index).x, h));
                b_index++;
            } else {
                // Keep track of both building heights to continue building skyline
                ha = a.get(a_index).y;
                hb = b.get(b_index).y;
                // If two buildings start at the same x, add taller building (greatest y)
                skyline.add(a.get(a_index).y > b.get(b_index).y ? new Point(a.get(a_index).x, ha) : new Point(b.get(b_index).x, hb));
                a_index++;
                b_index++;
                i++;
            }
            // Remove redundant skyline points (consecutive points with the same y)
            if (skyline.size() >= 2 && skyline.get(skyline.size()-2).y == skyline.get(skyline.size()-1).y) {
                skyline.remove(skyline.size()-1);
            }
            i++;
        }
        return skyline;
    }

}


