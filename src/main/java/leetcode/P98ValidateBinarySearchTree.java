package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
Given the root of a binary tree, determine if it is a valid binary search tree (BST).

A valid BST is defined as follows:

    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.

Example 1:

Input: root = [2,1,3]
Output: true

Example 2:

Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.

Constraints:
    The number of nodes in the tree is in the range [1, 104].
    -231 <= Node.val <= 231 - 1

 */
public class P98ValidateBinarySearchTree {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }
        int rootValue = root.val;

        int maxFromLeft = root.left != null ? max(root.left): Integer.MIN_VALUE ;
        int minFromRight = root.right != null ? min(root.right): Integer.MAX_VALUE;

        return isValidBST(root.left) && isValidBST(root.right) && rootValue > maxFromLeft && (rootValue < minFromRight);
    }

    private int min(TreeNode right) {
        if(right.left ==null) return right.val;
        else return min(right.left);
    }

    private int max(TreeNode root) {
        if(root.right ==null) return root.val;
        else return max(root.right);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,1,4,null,2;true",
            "5,3,6,2,4,null,null,1;true",
            "5;true",
            "1,1;false"
    })
    void test(String treeStr, String expected) {
        List<TreeNode> treeNodes = Arrays.stream(treeStr.split(",")).map(str -> {
            if ("null".equals(str)) return new TreeNode(-1);
            else return new TreeNode(Integer.parseInt(str));
        }).collect(Collectors.toList());
        TreeNode root = createTree(treeNodes);
        Assert.assertEquals(Boolean.valueOf(expected), isValidBST(root));

    }

    private TreeNode createTree(List<TreeNode> treeNodes) {
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
