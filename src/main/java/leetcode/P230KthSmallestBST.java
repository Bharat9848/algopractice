package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/*
230. Kth Smallest Element in a BST
Medium

Given the root of a binary search tree, and an integer k, return the kth (1-indexed) smallest element in the tree.
Example 1:

Input: root = [3,1,4,null,2], k = 1
Output: 1
Example 2:
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3

Constraints:

    The number of nodes in the tree is n.
    1 <= k <= n <= 104
    0 <= Node.val <= 104


Follow up: If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?
 */
public class P230KthSmallestBST {

        private class Pair<K,V>{
            K first;
            V sec;

            public Pair(K first, V sec) {
                this.first = first;
                this.sec = sec;
            }

            public void setFirst(K first1){
                this.first = first1;
            }

            public void setSec(V sec1){
                this.sec = sec1;
            }


            public K getFirst() {
                return first;
            }

            public V getSec() {
                return sec;
            }

            public String toString(){
                return "("+first+ ", " + sec +")";
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                util.Pair<?, ?> pair = (util.Pair<?, ?>) o;

                if (!first.equals(first)) return false;
                return sec.equals(sec);
            }

            @Override
            public int hashCode() {
                int result = first.hashCode();
                result = 31 * result + sec.hashCode();
                return result;
            }
        }
        private class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode() {
            }

            TreeNode(int val) {
                this.val = val;
            }

            TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }

    int kthSmallest(TreeNode root, int k) {
        Pair<Pair<Boolean, Integer>, Integer> resultHolder = new Pair<>(new Pair<>(Boolean.FALSE, -1), -1);
        kthSmallestRec(root, k, resultHolder);
        return resultHolder.getSec();
    }

    private void kthSmallestRec(TreeNode root, int k, Pair<Pair<Boolean, Integer>, Integer> resultHolder) {
//        if(resultHolder.getSec() == k) return;

        if(root==null && !resultHolder.getFirst().getFirst()){
            resultHolder.getFirst().setSec(0);
            resultHolder.getFirst().setFirst(Boolean.TRUE);
            return;
        }else if(root == null) return;

        kthSmallestRec(root.left, k, resultHolder);

        if(resultHolder.getFirst().getSec() == k){
            return;
        }else {
            resultHolder.getFirst().setSec(resultHolder.getFirst().getSec() + 1);
            if(resultHolder.getFirst().getSec() == k ){
                resultHolder.setSec(root.val);
                return;
            }
        }
        kthSmallestRec(root.right, k, resultHolder);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,1,4,null,2;1;1",
            "5,3,6,2,4,null,null,1;3;3",
            "5;1;5"
    })
    void test(String treeStr, int k, int expected) {
        List<TreeNode> treeNodes = Arrays.stream(treeStr.split(",")).map(str -> {
            if ("null".equals(str)) return new TreeNode(-1);
            else return new TreeNode(Integer.parseInt(str));
        }).collect(Collectors.toList());
        TreeNode root = createTree(treeNodes);
        Assert.assertEquals(expected, kthSmallest(root, k));

    }

    private TreeNode createTree(List<TreeNode> treeNodes) {
        TreeNode root = treeNodes.remove(0), currParent;
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            currParent = queue.remove(0);
            TreeNode temp;
            if (!treeNodes.isEmpty()) {
                temp = treeNodes.remove(0);
                if (temp.val != -1) {
                    currParent.left = temp;
                    queue.add(temp);
                }
            }
            if (!treeNodes.isEmpty()) {
                temp = treeNodes.remove(0);
                if (temp.val != -1) {
                    currParent.right = temp;
                    queue.add(temp);
                }
            }
        }
        return root;
    }
}
