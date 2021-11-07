package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given the root of a binary tree, flatten the tree into a "linked list":
 *
 *     The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
 *     The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,2,5,3,4,null,6]
 * Output: [1,null,2,null,3,null,4,null,5,null,6]
 *
 * Example 2:
 *
 * Input: root = []
 * Output: []
 *
 * Example 3:
 *
 * Input: root = [0]
 * Output: [0]
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 2000].
 *     -100 <= Node.val <= 100
 *
 *
 * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
 *
 */
public class P114FlattenBinaryTreeToLinklist {
    public void flatten(TreeNode root) {
        if(root != null){
            TreeNode temp = root.right;
            if(root.left != null){
                root.right = root.left;
                TreeNode leftBiggestNode = preOrderPrev(root.left);
                leftBiggestNode.right = temp;
                root.left = null;
            }
            flatten(root.right);
        }
    }

    private TreeNode preOrderPrev(TreeNode tree) {
        if(tree.left == null && tree.right == null) {
            return tree;
        }
        if(tree.right == null) {
            return preOrderPrev(tree.left);
        }
        return preOrderPrev(tree.right);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "1,2,5,3,4,null,6|1,null,2,null,3,null,4,null,5,null,6", "1,2,3|1,null,2,null,3,null","0|0"
    })
    void test(String inputStr, String expectedStr){
        TreeNode treeFromStringArr = TreeNode.createTreeFromStringArr(inputStr);
        flatten(treeFromStringArr);
        Assert.assertEquals(TreeNode.createTreeFromStringArr(expectedStr), treeFromStringArr);
    }


}
