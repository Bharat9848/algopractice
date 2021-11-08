package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.List;

/**
 * Given the root of a binary tree, return the sum of values of its deepest leaves.
 * Example 1:
 *
 * Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
 * Output: 15
 *
 * Example 2:
 *
 * Input: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 * Output: 19
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 104].
 *     1 <= Node.val <= 100
 *
 *     Do level order traversal and check the current set of level nodes whether they are at deepest level. if yes then do the sum.
 */
class P1302DeepestLeavesSum {
    int deepestLeavesSum(TreeNode root) {
        if(root==null) return 0;
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!isAllNodesAtDeepest(queue)){
            List<TreeNode> childNodes = new LinkedList<>();
            while(!queue.isEmpty()){
                TreeNode node = queue.remove(0);
                if(node.left != null){
                    childNodes.add(node.left);
                }
                if(node.right != null){
                    childNodes.add(node.right);
                }
            }
            queue = childNodes;
        }
        return queue.stream().mapToInt(t -> t.val).sum();

    }

    private boolean isAllNodesAtDeepest(List<TreeNode> queue) {
        return queue.stream().allMatch(treeNode -> treeNode.right==null && treeNode.left == null);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|1",
            "1,2,3|5",
            "1,null,2,null,3|3",
            "6,7,8,2,7,1,3,9,null,1,4,null,null,null,5|19",
            "1,2,3,4,5,null,6,7,null,null,null,null,8|15"
    })
    void test(String treeStr, int expected){
        Assert.assertEquals(expected, deepestLeavesSum(TreeNode.createTreeFromStringArr(treeStr)));
    }
}