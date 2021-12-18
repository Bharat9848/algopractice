package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given the root of a binary tree, return the length of the longest consecutive sequence path.
 *
 * The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path needs to be from parent to child (cannot be the reverse).
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,null,3,2,4,null,null,null,5]
 * Output: 3
 * Explanation: Longest consecutive sequence path is 3-4-5, so return 3.
 *
 * Example 2:
 *
 * Input: root = [2,null,3,2,null,1]
 * Output: 2
 * Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 3 * 104].
 *     -3 * 104 <= Node.val <= 3 * 104
 */
public class P298BinaryTreeLongestConsecutiveSeq {
    public int longestConsecutive(TreeNode root) {
        if(root==null){
            return -1;
        }
        Pair<Integer, Integer> consLengthToNonConsLength = calConsAndNonConsLength(root);
        return Math.max(consLengthToNonConsLength.getFirst(), consLengthToNonConsLength.getSec());
    }

    private Pair<Integer, Integer> calConsAndNonConsLength(TreeNode root) {
        if (root.left == null && root.right == null) {
            return new Pair<>(1, 0);
        }

        Pair<Integer, Integer> resultLeft = new Pair<>(0, 0);
        if (root.left != null) {
            Pair<Integer, Integer> consLengthvsNonConsLengthLeft = calConsAndNonConsLength(root.left);
            if (root.left.val == root.val + 1) {
                resultLeft =  new Pair<>(consLengthvsNonConsLengthLeft.getFirst() + 1, consLengthvsNonConsLengthLeft.getSec());
            } else {
                resultLeft = new Pair<>(1, Math.max(consLengthvsNonConsLengthLeft.getFirst(), consLengthvsNonConsLengthLeft.getSec()));
            }
        }
        Pair<Integer, Integer> resultRight = new Pair<>(0, 0);
        if(root.right != null){
            Pair<Integer, Integer> consLengthvsNonConsLengthRight = calConsAndNonConsLength(root.right);
            if (root.right.val == root.val + 1) {
                resultRight = new Pair<>(consLengthvsNonConsLengthRight.getFirst() + 1, consLengthvsNonConsLengthRight.getSec());
            } else {
                resultRight = new Pair<>(1, Math.max(consLengthvsNonConsLengthRight.getFirst(), consLengthvsNonConsLengthRight.getSec()));
            }
        }
        return new Pair<>(Math.max(resultLeft.getFirst(), resultRight.getFirst()), Math.max(resultLeft.getSec(), resultRight.getSec()));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2,null,3,2,null,1|2|consecutive sum is biggest",
            "2,null,null|1|single Node",
            "1,2,2,3,5|3|left side consecutive sum is larger than right side",
            "1,2,null,3,null,4,null,5,null|5|single linklist",
            "10,2,null,3,null,4,null,5,null|4|single linklist"
    })
    void test(String treeStr, int expected, String caseMsg){
        Assert.assertEquals(caseMsg,expected, longestConsecutive(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
