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

import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;
import util.Pair;

public class P543DiameterOfBinaryTree {
    class TreeNode  {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val){
            this.val = val;
        }
    }
    public int diameterOfBinaryTree(TreeNode root) {
        int length = 0;
        if (root != null) {
            Pair<Integer, Integer> l = lenAndDia(root.left);
            Pair<Integer, Integer> r = lenAndDia(root.right);
            int diaRoot = l.getFirst() + r.getFirst() + 1;
            length = Math.max(diaRoot, Math.max(l.getSec(), r.getSec()));
        }
        return length;
    }

    private Pair<Integer,Integer> lenAndDia(TreeNode tree) {
        if(tree==null){return new Pair<>(0,0);}
        if((tree.left==null && tree.right==null)){return  new Pair<>(1,1);}
        Pair<Integer, Integer> leftLenAndDia = lenAndDia(tree.left);
        Pair<Integer,Integer> rightLenAndDia = lenAndDia(tree.right);
        int length = Math.max(leftLenAndDia.getFirst(),rightLenAndDia.getFirst())+1;
        int dia = leftLenAndDia.getFirst() + rightLenAndDia.getFirst() + 1;
        int d = Math.max(dia,Math.max(leftLenAndDia.getSec(),rightLenAndDia.getSec()));
        return new Pair<>(length,d);
    }


    @Test
    public  void test(){
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        TreeNode n10 = new TreeNode(10);
        TreeNode n11 = new TreeNode(11);
        Assert.assertEquals(1,diameterOfBinaryTree(n1));
        n1.left = n2;
        Assert.assertEquals(2,diameterOfBinaryTree(n1));
        n1.right = n3;
        Assert.assertEquals(3,diameterOfBinaryTree(n1));
        n2.left = n4;
        Assert.assertEquals(4,diameterOfBinaryTree(n1));
        n2.right = n5;
        Assert.assertEquals(4,diameterOfBinaryTree(n1));
        n5.left = n6;
        Assert.assertEquals(5, diameterOfBinaryTree(n1));
        n5.right = n7;
        Assert.assertEquals(5, diameterOfBinaryTree(n1));
        n6.left = n8;
        Assert.assertEquals(6, diameterOfBinaryTree(n1));
        n8.left = n9;
        Assert.assertEquals(7, diameterOfBinaryTree(n1));
        n7.right = n10;
        Assert.assertEquals(7, diameterOfBinaryTree(n1));
        n10.right = n11;
        Assert.assertEquals(7,diameterOfBinaryTree(n1));
        TreeNode n12 = new TreeNode(12);
        TreeNode n13 = new TreeNode(13);
        n9.left = n12;
        n11.left = n13;
        Assert.assertEquals(9,diameterOfBinaryTree(n1));
    }
}
