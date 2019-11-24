package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tree.TreePractice;

/**We are given the head node root of a binary tree, where additionally every node's value is either a 0 or a 1.

 Return the same tree where every subtree (of the given tree) not containing a 1 has been removed.

 (Recall that the subtree of a node X is X, plus every node that is a descendant of X.)
 Example 1:
 Input: [1,null,0,0,1]
 Output: [1,null,0,null,1]
 Input: [1,0,1,0,0,0,1]
 Output: [1,null,1,null,1]

 Example 3:
 Input: [1,1,0,1,1,0,1,0]
 Output: [1,1,0,1,1,null,1]

 * Created by bharat on 9/5/18.
 */
public class P814BTreePruning {


    public TreeNode pruneTree(TreeNode root) {
        if(root==null){return null;}
        if(root.getVal() == 0 && root.getLeft()==null && root.getRight() == null){ return null;}
        if(root.getVal() == 1 && root.getLeft()==null && root.getRight() == null){ return root;}

        root.setLeft(pruneTree(root.getLeft()));
        root.setRight( pruneTree(root.getRight()));
        if(root.getVal()==0 && root.getLeft()==null && root.getRight()==null){
            return null;
        }
        return root;
    }

    @Test
    public void test1(){
        TreeNode allzero = new TreeNode(0);
        TreeNode o = new TreeNode(0);
        allzero.setRight( o);
        TreeNode c = new TreeNode(0);
        allzero.setLeft(c);
        assertNull(pruneTree(allzero));


        //case 2
        TreeNode z1 = new TreeNode(1);
        TreeNode z2 = new TreeNode(1);
        TreeNode z21 = new TreeNode(0);
        z1.setLeft(z2);
        z1.setRight( z21);
        TreeNode z3 = new TreeNode(1);
        TreeNode z4 = new TreeNode(1);
        TreeNode z221 = new TreeNode(0);
        TreeNode z5 = new TreeNode(1);
        z21.setLeft(z221);z21.setRight( z5);
        z2.setLeft(z3); z2.setRight( z4);
        TreeNode z = new TreeNode(0);
        z221.setLeft(z);
        System.out.println("arg:" + TreePractice.levelOrderTraversal(z1));
        System.out.println(TreePractice.levelOrderTraversal(pruneTree(z1)));

        //case 3 Input: [1,null,0,0,1]
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(0);
        a.setRight(b);
        TreeNode b1 = new TreeNode(0);
        TreeNode b2 = new TreeNode(1);
        b.setLeft(b1);
        b.setRight(b2);
        System.out.println("arg:" + TreePractice.levelOrderTraversal(a));
        System.out.println(TreePractice.levelOrderTraversal(pruneTree(a)));

    }
}
