package leetcode;

import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import static java.lang.Math.abs;

/**
 * Given a binary tree, determine if it is height-balanced.
 * <p>
 * For this problem, a height-balanced binary tree is defined as:
 * <p>
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 * Created by bharat on 8/4/18.
 */
public class P110TreeBalanced {
    private class TreeNode {
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

    }
}
