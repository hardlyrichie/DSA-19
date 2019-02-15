import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Boomerang {

    public static int numberOfBoomerangs(int[][] points) {
        if (points.length < 3) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < points.length; i++) {
            Map<Double, Integer> boomarangs = new HashMap<>();
            for (int j = 0; j < points.length;j++) {
                // Don't count boomerang with itself
                if (i == j) {
                    continue;
                }
                double d = distance(points[i], points[j]);
                if (boomarangs.get(d) != null) {
                    boomarangs.put(d, boomarangs.get(d) + 1);
                } else {
                    boomarangs.put(d, 1);
                }
            }
            Set<Map.Entry<Double, Integer>> entries = boomarangs.entrySet();

            for (Map.Entry<Double, Integer> entry : entries) {
                int n = entry.getValue();
                count += n*(n-1);
            }
        }

        return count;
    }

    private static double distance(int[] a, int[] b) {
        return Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));
    }
}

