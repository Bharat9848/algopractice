package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * You are given the root of a binary search tree (BST) and an integer val.
 *
 * Find the node in the BST that the node's value equals val and return the subtree rooted with that node. If such a node does not exist, return null.
 *
 * Input: root = [4,2,7,1,3], val = 2
 * Output: [2,1,3]
 * Input: root = [4,2,7,1,3], val = 5
 * Output: []
 *     The number of nodes in the tree is in the range [1, 5000].
 *     1 <= Node.val <= 107
 *     root is a binary search tree.
 *     1 <= val <= 107
 */
class P700SearchInBinaryTree {
    TreeNode searchBST(TreeNode root, int val) {
        TreeNode temp = root;
        while (temp != null){
            if(temp.val == val){
                return temp;
            }else if(temp.val > val){
                temp = temp.left;
            }else {
                temp = temp.right;
            }
        }
        return null;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "10,5,15,1,8|6|",
            "4,2,7,1,3|2|2,1,3",
            "4,2,7,1,3|5|",
    })
    void test(String treeStr, int val, String expectedStr){
        TreeNode expected = expectedStr == null ? null : TreeNode.createTreeFromStringArr(expectedStr);
        Assertions.assertEquals(expected, searchBST(TreeNode.createTreeFromStringArr(treeStr), val));
    }

}
