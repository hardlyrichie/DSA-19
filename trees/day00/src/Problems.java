import java.util.*;

public class Problems {

    // O(nlogn) b/c sorting is O(nlogn) and overpowers the rest which is O(logn)
    public static BinarySearchTree<Integer> minimalHeight(List<Integer> values) {
        Collections.sort(values);

        List<Integer> ordered = mhHelper(values, 0, values.size() - 1);

        BinarySearchTree<Integer> result = new BinarySearchTree<>();

        for (int i : ordered) {
            result.add(i);
        }

        return result;
    }

    public static List<Integer> mhHelper(List<Integer> values, int l, int r) {
        int mid = l + (r - l) / 2;

        List<Integer> order = new ArrayList<Integer>();

        if (Math.abs(l - r) < 3) {
            order.add(values.get(mid));
            for (int i = l; i <= r; i++) {
                if (i == mid) continue;
                order.add(values.get(i));
            }
            return order;
        } else {
            order.add(values.get(mid));
            List<Integer> leftList = mhHelper(values, l, mid - 1);
            for (int i : leftList) {
                order.add(i);
            }

            List<Integer> rightList = mhHelper(values, mid + 1, r);
            for (int i : rightList) {
                order.add(i);
            }
            return order;
        }

    }


    public static boolean isIsomorphic(TreeNode n1, TreeNode n2) {
        // TODO
        return false;
    }
}
