package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.AbstractMap;
import java.util.Map;

/*
A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

The path sum of a path is the sum of the node's values in the path.

Given the root of a binary tree, return the maximum path sum of any path.

Example 1:

Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

Example 2:

Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.

 Constraints:

    The number of nodes in the tree is in the range [1, 3 * 104].
    -1000 <= Node.val <= 1000

    1.MaxSum can be contiguous ot non contiguous
    2. at particular node contiguous sum is max(max(node.val +  contiguous of Left Subtree), (node.val + contiguous of right subtree), root.val);
    3. at particular node in non-contiguous sum  is max of following
    a. Contiguous of Left Subtree + contiguous of right Subtree + node.val
    b. left non-contiguous sum
    c. right non-contiguous sum
    d. left node val
    e. right node val
    f. left contiguous
    g. right contiguous
 */
class P124MaximumPathSumBinaryTree {

    int maxPathSum(TreeNode root) {
         if(root == null) return Integer.MIN_VALUE;

        Map.Entry<Integer, Integer> result = maxPathSumRec(root);
        return result.getValue()> result.getKey() ? result.getValue(): result.getKey();
    }

    private Map.Entry<Integer, Integer> maxPathSumRec(TreeNode root) {
        if(root == null) return new AbstractMap.SimpleEntry<>(Integer.MIN_VALUE, Integer.MIN_VALUE);
        if(root.left == null && root.right == null) return new AbstractMap.SimpleEntry<>(root.val, Integer.MIN_VALUE);
        Map.Entry<Integer, Integer> sumLeft = maxPathSumRec(root.left);
        Map.Entry<Integer, Integer> sumRight = maxPathSumRec(root.right);
        int contiguousSumLeft = sumLeft.getKey()==Integer.MIN_VALUE?0:sumLeft.getKey();
        int contiguousSumRight = sumRight.getKey()==Integer.MIN_VALUE?0:sumRight.getKey();
        int currentContiguousSum = Math.max(root.val, Math.max(contiguousSumLeft + root.val, contiguousSumRight + root.val));

        int currentNonContiguousSum = Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(contiguousSumLeft + root.val + contiguousSumRight, root.left != null? root.left.val: Integer.MIN_VALUE), root.right != null? root.right.val : Integer.MIN_VALUE), sumLeft.getValue()), sumRight.getValue()), sumLeft.getKey()), sumRight.getKey());
        return new AbstractMap.SimpleEntry<>(currentContiguousSum, currentNonContiguousSum);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3;6",
            "-10,9,20,null,null,15,7;42",
            "-1;-1",
            "-1,10,-2;10",
            "-1,null,9,-6,3,null,null,null,-2;12" //-1 -->9 --->-6
                                                          //|---->3--->-2
    })
    void testMaxPathSum(String treeStr, int expected){
        Assert.assertEquals(expected, maxPathSum(TreeNode.createTreeFromStringArr(treeStr)));
    }
}

