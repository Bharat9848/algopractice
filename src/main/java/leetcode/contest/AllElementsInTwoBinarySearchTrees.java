package leetcode.contest;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/*
Given two binary search trees root1 and root2.

Return a list containing all the integers from both trees sorted in ascending order.



Example 1:


Input: root1 = [2,1,4], root2 = [1,0,3]
Output: [0,1,1,2,3,4]
Example 2:

Input: root1 = [0,-10,10], root2 = [5,1,7,0,2]
Output: [-10,0,0,1,2,5,7,10]
Example 3:

Input: root1 = [], root2 = [5,1,7,0,2]
Output: [0,1,2,5,7]
Example 4:

Input: root1 = [0,-10,10], root2 = []
Output: [-10,0,10]
Example 5:


Input: root1 = [1,null,8], root2 = [8,1]
Output: [1,1,8,8]


Constraints:

Each tree has at most 5000 nodes.
Each node's value is between [-10^5, 10^5].
 */
public class AllElementsInTwoBinarySearchTrees {
    private class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }


    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> result = new ArrayList<>();
        MyIterator<Integer> iteratorRoot1 = new MyIterator<Integer>(root1);
        MyIterator<Integer> iteratorRoot2 = new MyIterator<Integer>(root2);
        while (iteratorRoot1.hasNext() && iteratorRoot2.hasNext()){
            int val1 = iteratorRoot1.peek();
            int val2 = iteratorRoot2.peek();
            if(val1 < val2){
                result.add(val1);
                iteratorRoot1.next();
            }else {
                result.add(val2);
                iteratorRoot2.next();
            }
        }
        if(!iteratorRoot1.hasNext()){
            while (iteratorRoot2.hasNext()){
                result.add(iteratorRoot2.next());
            }
        }
        if(!iteratorRoot2.hasNext()){
            while (iteratorRoot1.hasNext()){
                result.add(iteratorRoot1.next());
            }
        }
        return result;
    }
    private class MyIterator<T extends Integer> implements Iterator<Integer>{
        List<TreeNode> stack = new LinkedList<>();
        TreeNode root;
        public MyIterator(TreeNode root){
            this.root = root;
            if(root != null)
                fillStack(root);
        }

        private void fillStack(TreeNode root) {
            TreeNode current = root;
            stack.add(0, current);
            while(current.left != null){
                stack.add(0, current.left);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public Integer peek(){
            return stack.get(0).val;
        }
        @Override
        public Integer next() {
            TreeNode current = stack.remove(0);
            if(current.right != null){
                fillStack(current.right);
            }
            return current.val;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2,1,4;1,0,3;0,1,1,2,3,4",
            "0,-10,10;5,1,7,0,2;-10,0,0,1,2,5,7,10",
            ";5,1,7,0,2;0,1,2,5,7",
            "0,-10,10;;-10,0,10"
    })
    void test(String root1, String root2, String result){

        int[] rootNo = Arrays.stream((root1 == null? "":root1).split(","))
                .filter(str -> !str.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] root2No = Arrays.stream((root2==null ? "":root2).split(","))
                .filter(str -> !str.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
        TreeNode root1Tree = createTree(rootNo);
        TreeNode root2Tree = createTree(root2No);
        List<Integer> expected = Arrays.stream(result.split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
        Assert.assertEquals(expected, getAllElements(root1Tree,root2Tree));
    }

    private TreeNode createTree(int[] rootNo) {
        if(rootNo.length == 0) return null;
        TreeNode root =  new TreeNode(rootNo[0]);
        for (int i = 1; i < rootNo.length; i++) {
            insert(root, rootNo[i]);
        }
        return root;
    }

    private void insert(TreeNode root, int i) {
        TreeNode current = root;
        TreeNode prev = null;
        while (current != null){
            prev = current;
            if(i < current.val){
             current = current.left;
         }else{
             current = current.right;
         }
        }
        if(i < prev.val){
            prev.left = new TreeNode(i);
        }else{
            prev.right = new TreeNode(i);
        }
    }
}
