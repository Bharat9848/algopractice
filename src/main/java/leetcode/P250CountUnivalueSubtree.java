package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given the root of a binary tree, return the number of uni-value subtrees.
 *
 * A uni-value subtree means all nodes of the subtree have the same value.
 * Input: root = [5,1,5,5,5,null,5]
 * Output: 4
 * Input: root = []
 * Output: 0
 * Input: root = [5,5,5,5,5,null,5]
 * Output: 6
 * Constraints:
 *
 *     The number of the node in the tree will be in the range [0, 1000].
 *     -1000 <= Node.val <= 1000
 */
public class P250CountUnivalueSubtree {
    int countUnivalSubtrees(TreeNode root) {
        if(root == null){
            return 0;
        }
        Pair<Pair<Boolean,Integer>, Integer> currentNodeUniVsNoOfSub = countUnivalSubtreesRec(root);
        return currentNodeUniVsNoOfSub.getSec();
    }

    private Pair<Pair<Boolean, Integer>, Integer> countUnivalSubtreesRec(TreeNode root) {
        if(root.left == null && root.right == null){
            return new Pair<>(new Pair<>(true, root.val), 1);
        }
        Pair<Pair<Boolean, Integer>, Integer> leftResult = new Pair<>(new Pair<>(true, root.val), 0);
        if(root.left != null){
            leftResult = countUnivalSubtreesRec(root.left);
        }
        Pair<Pair<Boolean, Integer>, Integer> rightResult = new Pair<>(new Pair<>(true, root.val), 0);
        if(root.right != null){
            rightResult = countUnivalSubtreesRec(root.right);
        }
        if(leftResult.getFirst().getFirst() && rightResult.getFirst().getFirst() && leftResult.getFirst().getSec() == root.val && rightResult.getFirst().getSec() == root.val){
            return new Pair<>(new Pair<>(true, root.val), leftResult.getSec() + rightResult.getSec() + 1);
        }else {
            return new Pair<>(new Pair<>(false, root.val), leftResult.getSec() + rightResult.getSec());
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5,1,5,5,5,null,5|4",
            "|0",
            "5,5,5,5,5,null,5|6"
    })
    void test(String treeStr, int expected){
        Assertions.assertEquals(expected, countUnivalSubtrees(treeStr==null? null: TreeNode.createTreeFromStringArr(treeStr)));
    }
}
