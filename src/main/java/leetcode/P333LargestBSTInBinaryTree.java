package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given the root of a binary tree, find the largest subtree, which is also a Binary Search Tree (BST), where the largest means subtree has the largest number of nodes.
 *
 * A Binary Search Tree (BST) is a tree in which all the nodes follow the below-mentioned properties:
 *
 *     The left subtree values are less than the value of their parent (root) node's value.
 *     The right subtree values are greater than the value of their parent (root) node's value.
 *
 * Note: A subtree must include all of its descendants.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [10,5,15,1,8,null,7]
 * Output: 3
 * Explanation: The Largest BST Subtree in this case is the highlighted one. The return value is the subtree's size, which is 3.
 *
 * Example 2:
 *
 * Input: root = [4,2,7,2,3,5,null,2,null,null,null,null,null,1]
 * Output: 2
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 104].
 *     -104 <= Node.val <= 104
 *
 *
 *
 * Follow up: Can you figure out ways to solve it with O(n) time complexity?
 */
class P333LargestBSTInBinaryTree {
    int largestBSTSubtree(TreeNode root) {
        if(root == null){
            return 0;
        }
        return largestBSTSubtreeRec(root).getSec().getFirst();
    }

    private Pair<Boolean, Pair<Integer, Pair<Integer,Integer>>> largestBSTSubtreeRec(TreeNode root) {
        if (root.left == null && root.right == null) {
            return new Pair<>(true, new Pair<>(1, new Pair<>(root.val, root.val)));
        }
        Pair<Boolean, Pair<Integer, Pair<Integer, Integer>>> isBSTTotalMinMaxLeftSubtree = new Pair<>(true, new Pair<>(0, new Pair<>(null, null)));
        if (root.left != null) {
            isBSTTotalMinMaxLeftSubtree = largestBSTSubtreeRec(root.left);
        }
        Pair<Boolean, Pair<Integer, Pair<Integer, Integer>>> isBSTTotalMinMaxRightSubtree = new Pair<>(true, new Pair<>(0, new Pair<>(null, null)));
        if (root.right != null) {
            isBSTTotalMinMaxRightSubtree = largestBSTSubtreeRec(root.right);
        }
        if (isBSTTotalMinMaxLeftSubtree.getFirst() && isBSTTotalMinMaxRightSubtree.getFirst()) {
            Integer leftLargest = isBSTTotalMinMaxLeftSubtree.getSec().getSec().getSec();
            Integer rightSmallest = isBSTTotalMinMaxRightSubtree.getSec().getSec().getFirst();
            if ((leftLargest == null || root.val > leftLargest) && (rightSmallest == null || root.val < rightSmallest)) {
                //it is BST
                int leftTotal = isBSTTotalMinMaxLeftSubtree.getSec().getFirst();
                int rightTotal = isBSTTotalMinMaxRightSubtree.getSec().getFirst();
                Integer leftSmallest = isBSTTotalMinMaxLeftSubtree.getSec().getSec().getFirst();
                if(leftSmallest == null){
                    leftSmallest = root.val;
                }
                Integer rightLargest = isBSTTotalMinMaxRightSubtree.getSec().getSec().getSec();
                if(rightLargest == null){
                    rightLargest = root.val;
                }
                return new Pair<>(true, new Pair<>(leftTotal + rightTotal + 1, new Pair<>(leftSmallest, rightLargest)));
            }
        }
        Pair<Boolean, Pair<Integer, Pair<Integer, Integer>>> largestSubTreeWithLargestBST;
        if (isBSTTotalMinMaxLeftSubtree.getSec().getFirst() > isBSTTotalMinMaxRightSubtree.getSec().getFirst()) {
            largestSubTreeWithLargestBST = isBSTTotalMinMaxLeftSubtree;
        } else {
            largestSubTreeWithLargestBST = isBSTTotalMinMaxRightSubtree;
        }
        return new Pair<>(false, new Pair<>(largestSubTreeWithLargestBST.getSec().getFirst(), new Pair<>(largestSubTreeWithLargestBST.getSec().getSec().getFirst(), largestSubTreeWithLargestBST.getSec().getSec().getSec())));
        }



    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|1",
            "2,1,3|3",
            "4,2,7,2,3,5,null,2,null,null,null,null,null,1|2",
            "10,5,15,1,8,null,7|3"
    })
    void test(String treeStr, int expected){
        TreeNode root = TreeNode.createTreeFromStringArr(treeStr);
        Assert.assertEquals(expected, largestBSTSubtree(root));
    }
}
