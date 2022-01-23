package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given the root of a binary tree and a node u in the tree, return the nearest node on the same level that is to the right of u, or return null if u is the rightmost node in its level.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,2,3,null,4,5,6], u = 4
 * Output: 5
 * Explanation: The nearest node on the same level to the right of node 4 is node 5.
 *
 * Example 2:
 *
 * Input: root = [3,null,4,2], u = 2
 * Output: null
 * Explanation: There are no nodes to the right of 2.
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 105].
 *     1 <= Node.val <= 105
 *     All values in the tree are distinct.
 *     u is a node in the binary tree rooted at root.
 */
class findNearestRightNodeInBinaryTree {
    TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
        if(root == null){
            return null;
        }
        List<TreeNode> level = new LinkedList<>();
        level.add(root);
        while (!level.isEmpty()){
            List<TreeNode> nextLevel = new LinkedList<>();
            while(!level.isEmpty()){
                TreeNode node = level.remove(0);
                if(node.val == u.val){
                    return level.isEmpty() ? null : level.remove(0);
                }
                if(node.left != null){
                    nextLevel.add(node.left);
                }
                if(node.right != null){
                    nextLevel.add(node.right);
                }
            }
            level = nextLevel;
        }
        return null;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,null,4,2|2|",
            "1,2,3,null,4,5,6|4|5",
            "1,2,3|2|3",
            "1,2,3|3|",
            "1,2,3|1|",
            "1,null,2|2|",
            "1,2,null|2|",
    })
    void test(String rootStr, int uVal, Integer expected){
        if(expected == null){
            Assert.assertNull(findNearestRightNode(TreeNode.createTreeFromStringArr(rootStr), new TreeNode(uVal)));
        }else {
            Assert.assertEquals(expected, findNearestRightNode(TreeNode.createTreeFromStringArr(rootStr), new TreeNode(uVal)).val);
        }
    }
}
