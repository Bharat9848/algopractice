package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
 *
 * Input: root = [1,2,2,3,4,4,3]
 * Output: true
 * Input: root = [1,2,2,null,3,null,3]
 * Output: false
 *
 *     The number of nodes in the tree is in the range [1, 1000].
 *     -100 <= Node.val <= 100
 *
 *
 * Follow up: Could you solve it both recursively and iteratively?
 */
class P101SymmetricTree {
    boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        if((root.left == null && root.right!=null) || (root.right == null && root.left != null)){
            return false;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode tree1, TreeNode tree2) {
        if(tree1 == null && tree2 == null){
            return true;
        }else if(tree1 == null){
            return false;
        }else if(tree2 == null){
            return false;
        }else {
            return (tree1.val == tree2.val) && isSymmetric(tree1.left, tree2.right) && isSymmetric(tree1.right, tree2.left);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|true",
            "1,null,2|false",
            "1,2,null|false",
            "1,2,2|true",
            "1,3,4|false",
            "1,3,3,4,null|false",
            "1,3,3,null,null,4,null|false",
            "1,2,2,3,4,4,3|true",
            "1,2,2,3,null,null,3,4,5,5,4|true"
    })
    void testHappy(String treeStr, boolean expected){
        Assertions.assertEquals(expected, isSymmetric(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
