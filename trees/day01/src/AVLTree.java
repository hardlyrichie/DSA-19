public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Delete a key from the tree rooted at the given node.
     */
    @Override
    TreeNode<T> delete(TreeNode<T> n, T key) {
        n = super.delete(n, key);
        if (n != null) {
            // update the height of the tree using the height of the left and right child and then check balance for n and all children
            int rightHeight = n.rightChild != null ? n.rightChild.height : -1;
            int leftHeight = n.leftChild != null ? n.leftChild.height : -1;
            n.height =  Math.max(leftHeight, rightHeight) + 1;
            return balance(n);
        }
        return null;
    }

    /**
     * Insert a key into the tree rooted at the given node.
     */
    @Override
    TreeNode<T> insert(TreeNode<T> n, T key) {
        n = super.insert(n, key);
        if (n != null) {
            // update the height of the tree using the height of the left and right child and then check balance for n and all children
            int rightHeight = n.rightChild != null ? n.rightChild.height : -1;
            int leftHeight = n.leftChild != null ? n.leftChild.height : -1;
            n.height =  Math.max(leftHeight, rightHeight) + 1;
            return balance(n);
        }
        return null;
    }

    /**
     * Delete the minimum descendant of the given node.
     */
    @Override
    TreeNode<T> deleteMin(TreeNode<T> n) {
        n = super.deleteMin(n);
        if (n != null) {
            n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
            return balance(n);
        }
        return null;
    }

    // Return the height of the given node. Return -1 if null.
    private int height(TreeNode<T> n) {
        if (n == null) return -1;
        n.height =  Math.max(height(n.leftChild), height(n.rightChild)) + 1;
        return n.height;
    }

    public int height() {
        return Math.max(height(root), 0);
    }

    // Restores the AVL tree property of the subtree. Return the head of the new subtree
    TreeNode<T> balance(TreeNode<T> n) {
        int bf = balanceFactor(n);
        if (bf > 1) { // very right heavy
            if (balanceFactor(n.rightChild) <= -1) { // right child is left heavy
                n.rightChild = rotateRight(n.rightChild);
            }
            n = rotateLeft(n);
        } else if (bf < -1) { // very left heavy
            if (balanceFactor(n.leftChild) >= 1) { // left child is right heavy
                n.leftChild = rotateLeft(n.leftChild);
            }
            n = rotateRight(n);
        }

        return n;
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     */
    private int balanceFactor(TreeNode<T> n) {
        int rightHeight = n.rightChild != null ? n.rightChild.height : -1;
        int leftHeight = n.leftChild != null ? n.leftChild.height : -1;
        return rightHeight - leftHeight;
    }

    /**
     * Perform a right rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateRight(TreeNode<T> n) {
        TreeNode<T> rotateHead = n.leftChild;
        TreeNode<T> beta = rotateHead.rightChild;
        rotateHead.rightChild = n;
        n.leftChild = beta;

        // Calculate new heights after rotation
        rotateHead.height = height(rotateHead);

        return rotateHead;
    }

    /**
     * Perform a left rotation on node `n`. Return the head of the rotated tree.
     */
    private TreeNode<T> rotateLeft(TreeNode<T> n) {
        TreeNode<T> rotateHead = n.rightChild;
        TreeNode<T> beta = rotateHead.leftChild;
        rotateHead.leftChild = n;
        n.rightChild = beta;

        // Calculate new heights after rotation
        rotateHead.height = height(rotateHead);

        return rotateHead;
    }
}
