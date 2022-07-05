package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import static java.lang.Math.abs;

/**
 * Given a binary tree, determine if it is height-balanced.
 * <p>
 * For this problem, a height-balanced binary tree is defined as:
 * <p>
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 * Input: root = [3,9,20,null,null,15,7]
 * Output: true
 * Input: root = [1,2,2,3,3,null,null,4,4]
 * Output: false
 * Input: root = []
 * Output: true
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 5000].
 *     -10^4 <= Node.val <= 10^4
 */
public class P110TreeBalanced {
   /* private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        return calHeightAndBalanced(root).getFirst();
    }

    private int myabs(int i) {
        if(i<0){
            return  -i;
        }
        return i;
    }

    private Pair<Boolean, Integer> calHeightAndBalanced(TreeNode node) {
        if(node==null){
            return new Pair<>(true,0);
        }


        Pair<Boolean, Integer> pairL = calHeightAndBalanced(node.left);

        Pair<Boolean, Integer> pairR = calHeightAndBalanced(node.right);
        if(myabs(pairL.getSec()-pairR.getSec())>1){
            return new Pair<>(false,0);
        }
        return new Pair<>(pairL.getFirst()&& pairR.getFirst(),mymax(pairL.getSec(),pairR.getSec())+1);
    }

    private int mymax(int a, int b) {
        return a>b? a:b;
    }

    @Test
    public void testHeight(){
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(1);
        TreeNode c = new TreeNode(1);
        TreeNode d = new TreeNode(1);
        TreeNode e = new TreeNode(1);
        a.left = b;
        a.right = c;
        b.left = d;
        TreeNode f = new TreeNode(1);
        TreeNode g = new TreeNode(1);
        c.right =f;
        f.right = e;
        d.left =g;
        P110TreeBalanced x = new P110TreeBalanced();
        Assert.assertFalse(x.isBalanced(a));

    }

    @Test
    public void testHeight1(){
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(1);
        TreeNode c = new TreeNode(1);
        TreeNode d = new TreeNode(1);
        TreeNode e = new TreeNode(1);
        a.right = b;
        b.right = d;
        TreeNode f = new TreeNode(1);
        TreeNode g = new TreeNode(1);
        d.right =g;
        P110TreeBalanced x = new P110TreeBalanced();
        Assert.assertFalse(x.isBalanced(a));

    }*/

    boolean isBalanced(TreeNode root) {
        Pair<Boolean, Integer> result = isBalancedInternal(root);
        return result.getFirst();
    }

    private Pair<Boolean, Integer> isBalancedInternal(TreeNode root) {
        if(root == null){
            return new Pair<>(true, 0);
        }
        Pair<Boolean, Integer> leftsideBalancedWithHeight = isBalancedInternal(root.left);
        Pair<Boolean, Integer> rightsideBalancedWithHeight = isBalancedInternal(root.right);
        Integer leftHeight = leftsideBalancedWithHeight.getSec();
        Integer rightHeight = rightsideBalancedWithHeight.getSec();
        return new Pair<>(leftsideBalancedWithHeight.getFirst() && rightsideBalancedWithHeight.getFirst() && Math.abs(leftHeight-rightHeight) <= 1 , Math.max(leftHeight, rightHeight) + 1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,9,20,null,null,15,7|true",
            "1,2,2,3,3,null,null,4,4|false",
                    "|true",
                    "1|true",
                    "1,2,null,3,null|false",
                    "1,null,2,3,null|false"
    })
    void test(String treeStr, boolean expected){
        Assertions.assertEquals(expected, isBalanced(treeStr == null ? null : TreeNode.createTreeFromStringArr( treeStr)));
    }
}
