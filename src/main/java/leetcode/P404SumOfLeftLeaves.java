package leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
Given the root of a binary tree, return the sum of all left leaves.

Example 1:

Input: root = [3,9,20,null,null,15,7]
Output: 24
Explanation: There are two left leaves in the binary tree, with values 9 and 15 respectively.

Example 2:

Input: root = [1]
Output: 0

Constraints:
    The number of nodes in the tree is in the range [1, 1000].
    -1000 <= Node.val <= 1000
 */
public class P404SumOfLeftLeaves {
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

    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }
        // only root
        if(root.left != null && root.left.left == null && root.left.right == null){
            return root.left.val + sumOfLeftLeaves(root.right);
        }
        // root with left leaf only
        return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
    }

    @Test
    public void test(){
        assertEquals(0, sumOfLeftLeaves(new TreeNode(3)));
        assertEquals(5, sumOfLeftLeaves(new TreeNode(3, new TreeNode(5), null)));
        assertEquals(24, sumOfLeftLeaves(new TreeNode(3, new TreeNode(9),new TreeNode(20,new TreeNode(15), new TreeNode(7)))));
    }
}
