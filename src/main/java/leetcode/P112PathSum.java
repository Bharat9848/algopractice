package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

     boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        List<Pair<TreeNode, Integer>> queue = new ArrayList<>();
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

    boolean hasPathSumRec(TreeNode root, int sum) {
         if(root == null){
             return false;
         }
        if(root.right == null && root.left == null){
            return sum == root.val;
        }
        boolean found  = false;
        if(root.right!=null){
            found = found | hasPathSumRec(root.right, sum - root.val);
        }
        if(root.left!=null){
            found =  found | hasPathSumRec(root.left, sum - root.val);
        }

        return found;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5|5|true",
            "5,0,9|5|true",
            "5|6|false",
            "5|0|false",
            "5,4,8,11,null,13,4,7,2,null,null,null,1|22|true",
            "5,9,null,9,null|23|true"
    })
    void test1(String treeStr, int target, boolean expected){
        Assert.assertEquals(expected, hasPathSumRec(TreeNode.createTreeFromStringArr(treeStr), target));
    }

}
