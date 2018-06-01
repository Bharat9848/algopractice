package leetcode;

import org.junit.Assert;
import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

 Note: A leaf is a node with no children.

 Example:

 Given the below binary tree and sum = 22,

 5
 / \
 4   8
 /   / \
 11  13  4
 /  \      \
 7    2      1
 return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

 .
 */
public class P112PathSum {

    private class TreeNode {
    int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        List<Pair<TreeNode,Integer>> queue = new ArrayList<>();
        queue.add(new Pair<>(root,sum));
        List<Pair<TreeNode,Integer>> next = new ArrayList<>();
        boolean found = false;
        while(!found && !queue.isEmpty()){
            while(!queue.isEmpty()){
                Pair<TreeNode,Integer> pair = queue.remove(0);
                TreeNode left = pair.getFirst().left;
                TreeNode right = pair.getFirst().right;
                int sumLeft = pair.getSec();
                if(left == null && right == null && sumLeft-pair.getFirst().val == 0 ){
                    found = true;
                    break;
                }
                if(left != null){
                    next.add(new Pair<>(left,sumLeft-pair.getFirst().val));
                }
                if(right !=null){
                    next.add(new Pair<>(right,sumLeft-pair.getFirst().val));
                }
            }
            queue = next;
            next = new ArrayList<>();
        }
        return found;
    }

    public boolean hasPathSumRec(TreeNode root, int sum) {
        if(root==null && sum == 0){
            return true;
        }
        if(root==null && sum != 0){
            return false;
        }
        return (hasPathSumRec(root.left,sum - root.val) || hasPathSumRec(root.right,sum - root.val));
    }

    @Test
    public void test1(){

        TreeNode root = new TreeNode(5);
        TreeNode leftR = new TreeNode(9);
        TreeNode leftLR = new TreeNode(9);
        root.left = leftR;
        leftR.left = leftLR;
        Assert.assertTrue(hasPathSumRec(root,23));
    }

}
