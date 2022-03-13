package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
617. Merge Two Binary Trees
Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not.

You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.

Example 1:

Input:
	Tree 1                     Tree 2
          1                         2
         / \                       / \
        3   2                     1   3
       /                           \   \
      5                             4   7
Output:
Merged tree:
	     3
	    / \
	   4   5
	  / \   \
	 5   4   7
Input: root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
Output: [3,4,5,5,4,null,7]
Input: root1 = [1], root2 = [1,2]
Output: [2,2]


Note: The merging process must start from the root nodes of both trees.
 */
class P617MergeBinaryTrees {
    TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null){
            return root2;
        }
        if(root2 == null){
            return root1;
        }
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,2,5|2,1,3,null,4,null,7|3,4,5,5,4,null,7",
                            "1|1,2|2,2",
    })
    void test(String treeStr, String tree2Str, String expectedStr){
        Assertions.assertEquals(TreeNode.createTreeFromStringArr(expectedStr), mergeTrees(TreeNode.createTreeFromStringArr(treeStr), TreeNode.createTreeFromStringArr(tree2Str)));
    }
}
