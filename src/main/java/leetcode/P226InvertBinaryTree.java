package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static core.ds.TreeNode.createTreeFromStringArr;

/*
Given the root of a binary tree, invert the tree, and return its root.

Example 1:

Input: root = [4,2,7,1,3,6,9]
Output: [4,7,2,9,6,3,1]

Example 2:

Input: root = [2,1,3]
Output: [2,3,1]

Example 3:

Input: root = []
Output: []
Constraints:

    The number of nodes in the tree is in the range [0, 100].
    -100 <= Node.val <= 100
 */
public class P226InvertBinaryTree {
    public TreeNode invertTree(TreeNode root){
        if(root == null) return null;
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = invertTree(root.right);
        newRoot.right = invertTree(root.left);
        return newRoot;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4,2,7,1,3,6,9;4,7,2,9,6,3,1",
            "2,1,3;2,3,1"
    })
    void testInvertTree(String inputTree, String expectedTreeStr){
        Assert.assertEquals(createTreeFromStringArr(expectedTreeStr), invertTree(createTreeFromStringArr(inputTree)));
    }
}
