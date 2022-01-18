package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * You are given the root node of a binary search tree (BST) and a value to insert into the tree. Return the root node of the BST after the insertion. It is guaranteed that the new value does not exist in the original BST.
 *
 * Notice that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any of them.
 *
 *  Input: root = [4,2,7,1,3], val = 5
 * Output: [4,2,7,1,3,5]
 * Explanation: Another accepted tree is:
 *
 * Example 2:
 *
 * Input: root = [40,20,60,10,30,50,70], val = 25
 * Output: [40,20,60,10,30,50,70,null,null,25]
 *
 * Example 3:
 *
 * Input: root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
 * Output: [4,2,7,1,3,5]
 *
 * Constraints:
 *
 *     The number of nodes in the tree will be in the range [0, 104].
 *     -108 <= Node.val <= 108
 *     All the values Node.val are unique.
 *     -108 <= val <= 108
 *     It's guaranteed that val does not exist in the original BST.
 */
class P701InsertInBST {

    TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null){
            return new TreeNode(val);
        }
        insertIntoBSTRec(root, val);
        return root;
    }

    private void insertIntoBSTRec(TreeNode root, int val) {
        if(root.left == null && root.val > val){
            root.left = new TreeNode(val);
            return;
        }
        if(root.right == null && root.val < val){
            root.right = new TreeNode(val);
            return;
        }
        if(root.val < val){
            insertIntoBSTRec(root.right, val);
        }
        if(root.val > val){
            insertIntoBSTRec(root.left, val);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,2,7,1,3,null,null,null,null,null,null|5|4,2,7,1,3,5",
            "40,20,60,10,30,50,70|25|40,20,60,10,30,50,70,null,null,25",
            "4,2,7,1,3|5|4,2,7,1,3,5",
            "|3|3"
    })
    void test(String treeStr, int insert, String expectedStr){
        Assert.assertEquals(TreeNode.createTreeFromStringArr(expectedStr), insertIntoBST(treeStr== null?null:TreeNode.createTreeFromStringArr(treeStr), insert));
    }
}
