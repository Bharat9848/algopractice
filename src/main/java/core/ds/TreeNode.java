package core.ds;

import com.sun.source.tree.Tree;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by bharat on 9/5/18.
 */
public class TreeNode {
    public static final TreeNode EMPTY = new TreeNode(null);

    public Integer val;
    public TreeNode left;
    public TreeNode right;

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

    public static TreeNode createSample(int size){
        Random random = new Random(231);
        TreeNode root = new TreeNode(random.nextInt());

        List<TreeNode> pool = IntStream.of(size - 1).mapToObj(i -> {
            int val = random.nextInt();
            TreeNode node = new TreeNode(val);
            return node;
        }).collect(Collectors.toList());

        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val.equals(treeNode.val) && ((left == null && ((TreeNode) o).left == null) || (left != null && treeNode.left != null && left.equals(treeNode.left))) && ((right == null && ((TreeNode) o).right == null) || (right != null && treeNode.right != null && right.equals(treeNode.right)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }

    public static TreeNode createTreeFromStringArr(String treeStr){
        List<TreeNode> treeNodes = Arrays.stream(treeStr.split(",")).map(str -> {
            if ("null".equals(str)) return new TreeNode(-1);
            else return new TreeNode(Integer.parseInt(str));
        }).collect(Collectors.toList());
        return createTree(treeNodes);
    }

    private static TreeNode createTree(List<TreeNode> treeNodes) {
        TreeNode root = treeNodes.remove(0), currParent;
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            currParent = queue.remove(0);
            TreeNode temp;
            if (!treeNodes.isEmpty()) {
                temp = treeNodes.remove(0);
                if (temp.val != -1) {
                    currParent.left = temp;
                    queue.add(temp);
                }
            }
            if (!treeNodes.isEmpty()) {
                temp = treeNodes.remove(0);
                if (temp.val != -1) {
                    currParent.right = temp;
                    queue.add(temp);
                }
            }
        }
        return root;
    }
}
