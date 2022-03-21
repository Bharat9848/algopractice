package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * Given the root of a Binary Search Tree and a target number k, return true if there exist two elements in the BST such that their sum is equal to the given target.
 * Input: root = [5,3,6,2,4,null,7], k = 9
 * Output: true
 * Input: root = [5,3,6,2,4,null,7], k = 28
 * Output: false
 *
 *
 *     The number of nodes in the tree is in the range [1, 104].
 *     -104 <= Node.val <= 104
 *     root is guaranteed to be a valid binary search tree.
 *     -105 <= k <= 105
 */
public class P653TwoSumIVBST {
    boolean findTarget(TreeNode root, int k) {
        if(root == null || (root.left == null && root.right == null)){
            return false;
        }
        MyIterator ascendingIterator = new MyIterator(root);
        MyReverseIterator descendingIterator = new MyReverseIterator(root);
        while (ascendingIterator.hasNext() && descendingIterator.hasNext() && ascendingIterator.peek()!= descendingIterator.peek()){
            TreeNode left = ascendingIterator.peek();
            TreeNode right = descendingIterator.peek();
            if(left.val + right.val == k){
                return true;
            }else if(left.val + right.val > k){
                descendingIterator.next();
            }else {
                ascendingIterator.next();
            }
        }
        return false;
    }
    private class MyIterator{
        TreeNode currentNode;
        Stack<TreeNode> stack = new Stack<>();
        MyIterator(TreeNode root){
            TreeNode temp = root;
            while (temp.left != null){
                stack.push(temp);
                temp = temp.left;
            }
            currentNode = temp;
        }
        boolean hasNext(){
            return currentNode != null;
        }
        TreeNode peek(){
           return currentNode;
        }
        int next(){
            int val = currentNode.val;
            if(currentNode.right != null){
                TreeNode temp = currentNode.right;
                while (temp.left != null){
                    stack.push(temp);
                    temp = temp.left;
                }
                currentNode = temp;
            }else{
                currentNode = stack.isEmpty() ? null :stack.pop();
            }
            return val;
        }
    }

    private class MyReverseIterator{
        TreeNode currentNode;
        Stack<TreeNode> stack = new Stack<>();
        MyReverseIterator(TreeNode root){
            TreeNode temp = root;
            while(temp.right != null){
                stack.push(temp);
                temp = temp.right;
            }
            currentNode = temp;
        }

        boolean hasNext(){
            return currentNode != null;
        }
        TreeNode peek(){
            return currentNode;
        }
        int next(){
            int val = currentNode.val;
            if(currentNode.left != null){
                TreeNode temp = currentNode.left;
                if(temp.right != null){
                    while (temp.right != null){
                        stack.push(temp);
                        temp = temp.right;
                    }
                }
                currentNode = temp;
            }else{
                currentNode = stack.isEmpty() ? null :stack.pop();
            }
            return val;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,null,7|12|false",
            "2,null,3|6|false",
            "5,3,6,2,4,null,7|9|true",
            "5,3,6,2,4,null,7|28|false",
            "5,3,6|8|true",
            "5,3,6|11|true",
            "5,3,6|2|false",
            "5,3,6|28|false",
    })
    void  test(String treeStr, int k, boolean expected){
        Assertions.assertEquals(expected, findTarget(TreeNode.createTreeFromStringArr(treeStr), k));
    }
}
