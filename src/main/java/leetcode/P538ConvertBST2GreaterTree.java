package leetcode;

import com.sun.source.tree.Tree;
import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.AbstractMap;
import java.util.Map;

/*
Given the root of a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

As a reminder, a binary search tree is a tree that satisfies these constraints:

    The left subtree of a node contains only nodes with keys less than the node's key.
    The right subtree of a node contains only nodes with keys greater than the node's key.
    Both the left and right subtrees must also be binary search trees.

Note: This question is the same as 1038: https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/



Example 1:

Input: root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]

Example 2:

Input: root = [0,null,1]
Output: [1,null,1]

Example 3:

Input: root = [1,0,2]
Output: [3,3,2]

Example 4:

Input: root = [3,2,4,1]
Output: [7,9,4,10]



Constraints:

    The number of nodes in the tree is in the range [0, 104].
    -104 <= Node.val <= 104
    All the values in the tree are unique.
    root is guaranteed to be a valid binary search tree.

   init steps = go till rightmost node in tree.
   typical node with left and right: left needs data from parent root .. root need data from right ~ every node needs the immediate greater number.
   prev greater number might not comes from the immediate child.
    a->b<-c

 */
 class P538ConvertBST2GreaterTree {
    TreeNode convertBST(TreeNode root){
        return greaterTreeRec(root, 0).getValue();
    }

    private Map.Entry<Integer, TreeNode> greaterTreeRec(TreeNode root, int greaterSum){
        if (root == null) {
            return new AbstractMap.SimpleEntry<>(greaterSum, null);
        }
        Map.Entry<Integer, TreeNode> rightSub = greaterTreeRec(root.right, greaterSum);
        TreeNode newRoot = new TreeNode(rightSub.getKey() + root.val);
        Map.Entry<Integer, TreeNode> leftSumToNode = greaterTreeRec(root.left, newRoot.val);
        newRoot.left = leftSumToNode.getValue();
        newRoot.right = rightSub.getValue();
        return new AbstractMap.SimpleEntry<>(leftSumToNode.getKey(), newRoot);
    }

    @ParameterizedTest
    @CsvSource( delimiter = ';', value = {
            "3,2,4;7,9,4",
            "3,2,4,1;7,9,4,10",
            "1,0,2;3,3,2",
            "0,null,1;1,null,1",
            "4,1,6,0,2,5,7,null,null,null,3,null,null,null,8;30,36,21,36,35,26,15,null,null,null,33,null,null,null,8"
    })
    void test(String inputStr, String expected){
        TreeNode actual = convertBST(TreeNode.createTreeFromStringArr(inputStr));
        Assert.assertEquals(TreeNode.createTreeFromStringArr(expected), actual);
    }
}
