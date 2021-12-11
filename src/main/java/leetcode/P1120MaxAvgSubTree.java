package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given the root of a binary tree, return the maximum average value of a subtree of that tree. Answers within 10-5 of the actual answer will be accepted.
 *
 * A subtree of a tree is any node of that tree plus all its descendants.
 *
 * The average value of a tree is the sum of its values, divided by the number of nodes.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [5,6,1]
 * Output: 6.00000
 * Explanation:
 * For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
 * For the node with value = 6 we have an average of 6 / 1 = 6.
 * For the node with value = 1 we have an average of 1 / 1 = 1.
 * So the answer is 6 which is the maximum.
 *
 * Example 2:
 *
 * Input: root = [0,null,1]
 * Output: 1.00000
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 104].
 *     0 <= Node.val <= 105
 */
class P1120MaxAvgSubTree {
     double maximumAverageSubtree(TreeNode root) {
        if(root == null) throw new RuntimeException("Invalid input");
        Pair<Double, Pair<Integer, Integer>> maxAvgVsSumAndCount  = maxAvgSubTreeRec(root);
        Double maxSubTreeAvg = maxAvgVsSumAndCount.getFirst();
        int nodeAvg = maxAvgVsSumAndCount.getSec().getFirst() / maxAvgVsSumAndCount.getSec().getSec();
        return maxSubTreeAvg > nodeAvg ? maxSubTreeAvg : nodeAvg;
    }

    private Pair<Double, Pair<Integer, Integer>> maxAvgSubTreeRec(TreeNode root) {
        if(root == null){
            return new Pair<>(0.0, new Pair<>(0, 0));
        }
        Pair<Double, Pair<Integer, Integer>> leftSubTreeMaxAvgVsSumAndCount = maxAvgSubTreeRec(root.left);
        Pair<Double, Pair<Integer, Integer>> rightSubTreeMaxAvgVsSumAndCount = maxAvgSubTreeRec(root.right);
        int totalSum = leftSubTreeMaxAvgVsSumAndCount.getSec().getFirst() +  rightSubTreeMaxAvgVsSumAndCount.getSec().getFirst() + root.val;
        int totalCount = leftSubTreeMaxAvgVsSumAndCount.getSec().getSec() +  rightSubTreeMaxAvgVsSumAndCount.getSec().getSec() + 1;
        Double maxAvg = Math.max((totalSum+ 0.0)/totalCount, Math.max(leftSubTreeMaxAvgVsSumAndCount.getFirst(), rightSubTreeMaxAvgVsSumAndCount.getFirst()));
        return new Pair<>(maxAvg, new Pair<>(totalSum, totalCount));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"5,6,1|6.000",
    "78|78.000",
    "0,null,1|1.000",
    "10,null,20,null,30|30.0000",
    "60,null,50,null,30|46.666666"
    })
    void test(String treeStr, double avg){
        TreeNode root = TreeNode.createTreeFromStringArr(treeStr);
        Assert.assertEquals(avg, maximumAverageSubtree(root),0.00001);
    }
}
