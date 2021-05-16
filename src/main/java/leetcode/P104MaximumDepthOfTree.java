package leetcode;

import org.junit.Assert;
import org.junit.Test;

/*
Given the root of a binary tree, return its maximum depth.

A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 Example 1:

Input: root = [3,9,20,null,null,15,7]
Output: 3

Example 2:

Input: root = [1,null,2]
Output: 2

Example 3:

Input: root = []
Output: 0

Example 4:

Input: root = [0]
Output: 1

Constraints:

    The number of nodes in the tree is in the range [0, 104].
    -100 <= Node.val <= 100

 */
public class P104MaximumDepthOfTree {
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

    int maxDepth(TreeNode root) {
        if(root == null) {return 0;}
        if(root.left == null && root.right == null) { return 1;}
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    @Test
    public void test(){
        Assert.assertEquals(0 , maxDepth(null));
        Assert.assertEquals(1 , maxDepth(new TreeNode(3)));
        Assert.assertEquals(2 , maxDepth(new TreeNode(3, new TreeNode(2), null)));
        Assert.assertEquals(3 , maxDepth(new TreeNode(3, new TreeNode(2, new TreeNode(4), null), null)));
    }
}
