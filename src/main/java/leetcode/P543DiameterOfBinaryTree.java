/*
Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree
          1
         / \
        2   3
       / \
      4   5
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.


Wrong assumption : diameter of Btree path includes root.
 */
package leetcode;
/**
 * Given the root of a binary tree, return the length of the diameter of the tree.
 *
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 *
 * The length of a path between two nodes is represented by the number of edges between them.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,2,3,4,5]
 * Output: 3
 * Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].
 *
 * Example 2:
 *
 * Input: root = [1,2]
 * Output: 1
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 104].
 *     -100 <= Node.val <= 100
 *
 *  Difficulty faced when I think about the base condition of (root == null)
 */

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

public class P543DiameterOfBinaryTree {
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        Pair<Integer, Integer> lengthVsDia = lengthAndDia(root);
        return Math.max(lengthVsDia.getFirst(), lengthVsDia.getSec());
    }

    private Pair<Integer, Integer> lengthAndDia(TreeNode node) {
        if(node == null) {
            return null;
        }
        if(node.left ==null && node.right == null){
            return new Pair<>(0,0);
        }
        Pair<Integer, Integer> leftLenAndDia = lengthAndDia(node.left);
        Pair<Integer, Integer> rightLenAndDia = lengthAndDia(node.right);
        int leftDia = leftLenAndDia == null ? -1 : (leftLenAndDia.getSec());
        int leftLen = leftLenAndDia == null ? -1 : (leftLenAndDia.getFirst());
        int rightDia = rightLenAndDia == null ? -1 : (rightLenAndDia.getSec());
        int rightLen = rightLenAndDia == null ? -1 : (rightLenAndDia.getFirst());

        Integer diaIncl = Math.max(leftLen+1 + rightLen+1, Math.max(leftDia, rightDia));
        Integer length = Math.max(leftLen, rightLen) + 1;
        return new Pair<>(length, diaIncl);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|0",
            "1,2|1",
            "1,2,3,4,5|3",
            "1,2,3|2"
    })
    public  void test(String treeStr, int expected){
        TreeNode tree = TreeNode.createTreeFromStringArr(treeStr);
        Assert.assertEquals(expected, diameterOfBinaryTree(tree));
    }
}
