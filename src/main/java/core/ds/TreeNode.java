package core.ds;

import java.util.List;
import java.util.Random;
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

}
