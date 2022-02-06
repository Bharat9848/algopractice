package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Given two binary search trees root1 and root2, return a list containing all the integers from both trees sorted in ascending order.
 *
 *
 *
 * Example 1:
 *
 * Input: root1 = [2,1,4], root2 = [1,0,3]
 * Output: [0,1,1,2,3,4]
 *
 * Example 2:
 *
 * Input: root1 = [1,null,8], root2 = [8,1]
 * Output: [1,1,8,8]
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in each tree is in the range [0, 5000].
 *     -105 <= Node.val <= 105
 *
 *     Create Inorder iterator for both tree and compare and assemble the result from two iterators one by one.
 */
 class P1305AllElementInBinarySearchTree {
    private class MyIterator{
        private Stack<TreeNode> nodesToProcess;
        private TreeNode currentNode;
        MyIterator(TreeNode root){
            nodesToProcess = new Stack<>();
            currentNode = root;
            while (currentNode != null){
                nodesToProcess.push(currentNode);
                currentNode = currentNode.left;
            }
            if(!nodesToProcess.isEmpty())
            currentNode = nodesToProcess.pop();
        }

        TreeNode peek(){
            return currentNode;
        }

        boolean hasNext(){
            return currentNode != null;
        }

        TreeNode next(){
            TreeNode toReturn = currentNode;
            if(currentNode.right != null){
                TreeNode temp = currentNode.right;
                if(temp.left != null){
                    while(temp != null){
                        nodesToProcess.push(temp);
                        temp = temp.left;
                    }
                    currentNode = nodesToProcess.pop();
                }else {
                    currentNode = temp;
                }
            }else{
                if(!nodesToProcess.isEmpty()){
                    currentNode = nodesToProcess.pop();
                }else{
                    currentNode = null;
                }
            }
            return toReturn;
        }
    }
    List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        MyIterator iterator = new MyIterator(root1);
        MyIterator iterator2 = new MyIterator(root2);
        List<Integer> result = new ArrayList<>();
        while (iterator.hasNext() && iterator2.hasNext()){
            if(iterator.peek().val < iterator2.peek().val){
                result.add(iterator.next().val);
            }else{
                result.add(iterator2.next().val);
            }
        }
        while(iterator.hasNext()){
            result.add(iterator.next().val);
        }
        while(iterator2.hasNext()){
            result.add(iterator2.next().val);
        }

        return result;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,null,59,57,90|60,17,74,6,20,63,97,null,null,null,null,null,null,95|0,6,17,20,57,59,60,63,74,90,95,97",
            "1,null,4,null,5|10,9,null,8,null,7|1,4,5,7,8,9,10",
            "2,1,4||1,2,4",
            "|1,0,3|0,1,3",
            "1|1|1,1",
            "2,1,4|1,0,3|0,1,1,2,3,4",
            "1,null,8|8,1|1,1,8,8"
    })
    void test(String tree1Str, String tree2Str, String expectedStr){
        List<Integer> expected = Arrays.stream(expectedStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        TreeNode root1 = tree1Str==null ? null: TreeNode.createTreeFromStringArr(tree1Str);
        TreeNode root2 = tree2Str == null ? null: TreeNode.createTreeFromStringArr(tree2Str);
        Assertions.assertEquals(expected, getAllElements(root1, root2));
    }
}
