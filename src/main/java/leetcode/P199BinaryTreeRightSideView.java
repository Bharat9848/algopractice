package leetcode;
/*
Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example:

Input: [1,2,3,null,5,null,4]
Output: [1, 3, 4]
Explanation:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
 */

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class P199BinaryTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        List<TreeNode> first = new ArrayList<>();
        List<TreeNode> sec = new ArrayList<>();
        first.add(root);
        while (!first.isEmpty() || !sec.isEmpty()){
            List<TreeNode> tempFilled = first.isEmpty() ? sec : first;
            List<TreeNode> tempEmpty = first.isEmpty() ? first : sec;
            while (!tempFilled.isEmpty()){
                TreeNode candidate = tempFilled.remove(0);
                if(candidate.left != null) tempEmpty.add(candidate.left);
                if(candidate.right != null) tempEmpty.add(candidate.right);
                if(tempFilled.isEmpty()){
                    result.add(candidate.val);
                }
            }
        }
        return result;
    }


    @Test
    public void testRView(){
        TreeNode root = new TreeNode(1);
        TreeNode root2 = new TreeNode(2);
        TreeNode root3 = new TreeNode(3);
        TreeNode root4 = new TreeNode(4);
        TreeNode root5 = new TreeNode(5);
        root.setLeft(root2);
        root.setRight(root3);
        root2.setLeft(root4);
        root2.setRight(root5);

        Assert.assertEquals(new ArrayList<Integer>(){{add(1); add(3); add(5);}}, rightSideView(root));
        Assert.assertEquals(new ArrayList<Integer>(){{add(1); add(3); add(5);}}, rightSideView(null));
    }
}
