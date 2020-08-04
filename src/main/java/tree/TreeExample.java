package tree;

import core.ds.TreeNode;

public class TreeExample {
    public static TreeNode treeBalanced9Nodes() {
        TreeNode root = new TreeNode(1);
        TreeNode val2 = new TreeNode(2);
        TreeNode val3 = new TreeNode(3);
        TreeNode val4 = new TreeNode(4);
        TreeNode val5 = new TreeNode(5);
        TreeNode val6 = new TreeNode(6);
        TreeNode val7 = new TreeNode(7);
        TreeNode val8 = new TreeNode(8);
        TreeNode val9 = new TreeNode(9);
        root.left = val2;
        root.right = val3;
        val2.left = val4;
        val2.right = val5;
        val3.left = val6;
        val3.right = val7;
        val7.left= val8;
        val7.right = val9;
        return root;
    }


}
