package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/*
Given the root node of a binary search tree and two integers low and high, return the sum of values of all nodes with a value in the inclusive range [low, high].



Example 1:

Input: root = [10,5,15,3,7,null,18], low = 7, high = 15
Output: 32
Explanation: Nodes 7, 10, and 15 are in the range [7, 15]. 7 + 10 + 15 = 32.

Example 2:

Input: root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10
Output: 23
Explanation: Nodes 6, 7, and 10 are in the range [6, 10]. 6 + 7 + 10 = 23.



Constraints:

    The number of nodes in the tree is in the range [1, 2 * 104].
    1 <= Node.val <= 105
    1 <= low <= high <= 105
    All Node.val are unique.


 */
public class P938RangeSumOfBST {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //TODO recursive solution
    public int rangeSumBST(TreeNode root, int low, int high){
        if(root==null){
            return 0;
        }
        if(root.val >= low && root.val <= high){
            return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
        }else if(root.val < low && root.val < high){
            return rangeSumBST(root.right, low, high);
        }else {
            return rangeSumBST( root.right, low, high);
        }
    }

    public int rangeSumBSTIterative(TreeNode root, int low, int high) {
        int sum = 0;
        List<TreeNode> inRange = new ArrayList<>();
        inRange.add(root);
        while (!inRange.isEmpty()) {
            TreeNode curr = inRange.remove(0);
            if (curr.val >= low && curr.val <= high) {
                if (curr.right != null) inRange.add(curr.right);
                if (curr.left != null) inRange.add(curr.left);
                sum = sum + curr.val;
            } else if (curr.val < low && curr.val < high) {
                if (curr.right != null) inRange.add(curr.right);
            } else {
                if (curr.left != null) inRange.add(curr.left);
            }
        }
        return sum;
    }

    @Test
    public void test1(){
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5, new TreeNode(3), new TreeNode(7));
        root.right = new TreeNode(15, null, new TreeNode(18));
        Assert.assertEquals(32, rangeSumBST(root, 7, 15));
    }

    @Test
    public void test2(){
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5, new TreeNode(3, new TreeNode(1), null), new TreeNode(7, new TreeNode(6), null));
        root.right = new TreeNode(15, new TreeNode(13), new TreeNode(18));
        Assert.assertEquals(23, rangeSumBST(root, 6, 10));
    }
}
