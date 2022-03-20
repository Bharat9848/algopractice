package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.
 *
 * Return the number of good nodes in the binary tree.
 * Input: root = [3,1,4,3,null,1,5]
 * Output: 4
 * Input: root = [3,3,null,4,2]
 * Output: 3
 * Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.
 * Input: root = [1]
 * Output: 1
 * Explanation: Root is considered as good.
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the binary tree is in the range [1, 10^5].
 *     Each node's value is between [-10^4, 10^4].
 */
class P1448CountGoodNodes {
    int goodNodes(TreeNode root) {
        return countGoodNodes(root, root.val);
    }

    private int countGoodNodes(TreeNode node, Integer maxSoFarBefore) {
        if(node == null){
            return 0;
        }
        int maxSoFar = Math.max(maxSoFarBefore, node.val);
        int leftCount = countGoodNodes(node.left, maxSoFar);
        int rightCount = countGoodNodes(node.right, maxSoFar);
        return leftCount + rightCount + (maxSoFarBefore <= node.val ? 1:0);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,1,4,3,null,1,5|4|Asymmteric tree",
            "3,3,null,4,2|3| Asymmetric tree",
            "1|1|single root",
    })
    void test(String treeStr, int expected, String message){
        System.out.println(message);
        Assertions.assertEquals(expected, goodNodes(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
