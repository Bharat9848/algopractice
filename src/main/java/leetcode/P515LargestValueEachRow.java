package leetcode;

import com.sun.source.tree.Tree;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).

Example 1:

Input: root = [1,3,2,5,3,null,9]
Output: [1,3,9]

Example 2:
Input: root = [1,2,3]
Output: [1,3]
Example 3:
Input: root = [1]
Output: [1]
Example 4:
Input: root = [1,null,2]
Output: [1,2]
Example 5:
Input: root = []
Output: []
Constraints:

    The number of nodes in the tree will be in the range [0, 104].
    -231 <= Node.val <= 231 - 1
 */
public class P515LargestValueEachRow {
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

    List<Integer> largestValues(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> maxList = new ArrayList<>();
        List<TreeNode> queue = new ArrayList<>();

        queue.add(root);
        int max = Integer.MIN_VALUE;
        List<TreeNode> nextLevel = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode curr = queue.remove(0);
            if (max < curr.val) {
                max = curr.val;
            }
            if (curr.left != null) nextLevel.add(curr.left);
            if (curr.right != null) nextLevel.add(curr.right);
            if (queue.isEmpty()) {
                queue = nextLevel;
                nextLevel = new ArrayList<>();
                maxList.add(max);
                max = Integer.MIN_VALUE;
            }
        }
        return maxList;
    }

    @Test
    public void test1(){
        TreeNode root = new TreeNode(1,new TreeNode(3, new TreeNode(5), new TreeNode(3)),new TreeNode(2,null,new TreeNode(9)));
        Assert.assertEquals(Arrays.asList(1,3,9), largestValues(root));

        TreeNode root1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        Assert.assertEquals(Arrays.asList(1,3), largestValues(root1));

        TreeNode root2 = new TreeNode(0, new TreeNode(-1), null);
        Assert.assertEquals(Arrays.asList(0,-1), largestValues(root2));

        TreeNode root3 = new TreeNode(3);
        Assert.assertEquals(Arrays.asList(3), largestValues(root3));

        Assert.assertTrue(largestValues(null).isEmpty());
    }
}
