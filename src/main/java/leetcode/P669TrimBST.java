package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a binary search tree and the lowest and highest boundaries as L and R,
 * trim the tree so that all its elements lies in [L, R] (R >= L). You might need to change the root of the tree,
 * so the result should return the new root of the trimmed binary search tree.
 */
public class P669TrimBST {
    
    @Test
    public void test1(){
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6);
        TreeNode g = new TreeNode(7);
        TreeNode h = new TreeNode(8);
        TreeNode i = new TreeNode(9);
        TreeNode j = new TreeNode(10);



        P669TrimBST x = new P669TrimBST();
        e.left = b;
        e.right = g;
        b.left = a;
        b.right = d;
        d.left = c;
        System.out.println(x.trimBST(e,3,4));
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        public String toString(){
            return left+" <==="+val+"===> "+ right;
        }
    }

    public TreeNode trimBST(TreeNode root, int L, int R) {
        if(root == null){
            return null;
        }
        int val = root.val;
        int inRange = isInRange(val, L, R);
        if(inRange ==0){
            if(root.left!=null){
             root.left = trimBST(root.left,L,val);   
            }
            if(root.right!=null){
                root.right = trimBST(root.right,val,R);
            }
            return root;
        }else if(inRange<0){
            TreeNode right = root.right;
            root.right = null;
            root.left = null;
            root = null;
            return trimBST(right,L,R);
        }else{
            TreeNode left= root.left;
            root.right = null;
            root.left = null;
            root = null;
            return trimBST(left,L,R);
        }
    }

    private int isInRange(int val, int l, int r) {
        if(val >=l && val<=r){return 0;}else if(val<l) {return -1;} else{return 1;}
    }
}
