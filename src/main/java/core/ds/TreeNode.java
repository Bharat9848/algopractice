package core.ds;

/**
 * Created by bharat on 9/5/18.
 */
public class TreeNode {
    public static final TreeNode EMPTY = new TreeNode(null);

    Integer val;
    TreeNode left;
    TreeNode right;

    public TreeNode(Integer x) {
        val = x;
    }

    public Integer getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
