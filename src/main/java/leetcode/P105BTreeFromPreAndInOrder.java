package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/*
Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.
Example 1:

Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]

Example 2:

Input: preorder = [-1], inorder = [-1]
Output: [-1]

Constraints:

    1 <= preorder.length <= 3000
    inorder.length == preorder.length
    -3000 <= preorder[i], inorder[i] <= 3000
    preorder and inorder consist of unique values.
    Each value of inorder also appears in preorder.
    preorder is guaranteed to be the preorder traversal of the tree.
    inorder is guaranteed to be the inorder traversal of the tree

 */
public class P105BTreeFromPreAndInOrder {
     TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0 && inorder.length == 0){
            return null;
        }
        if(preorder.length == 1 && inorder.length == 1){
            return new TreeNode(preorder[0]);
        }
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        int[] leftSubInorder = findInOrderSubArray(inorder, rootVal, true);
        int[] leftSubPreorder = findPreOrderSubArray(preorder, Arrays.stream(leftSubInorder).boxed().collect(Collectors.toSet()));
        root.left = buildTree(leftSubPreorder, leftSubInorder);

        int[] rightSubInOrder = findInOrderSubArray(inorder, rootVal, false);
        int[] rightSubPreOrder = findPreOrderSubArray(preorder, Arrays.stream(rightSubInOrder).boxed().collect(Collectors.toSet()));
        root.right = buildTree(rightSubPreOrder, rightSubInOrder);
        return root;
    }

    private int[] findPreOrderSubArray(int[] preorder, Set<Integer> inOrdArr) {
        return Arrays.stream(preorder).filter(inOrdArr::contains).toArray();
    }

    private boolean contains(int[] leftSubInorder, int find) {
        return Arrays.stream(leftSubInorder).anyMatch(ele -> ele == find);
    }

    private int[] findInOrderSubArray(int[] inorder, int rootVal, boolean findLeft) {
        if(findLeft){
            return Arrays.stream(inorder).takeWhile(ele -> ele != rootVal).filter(e -> e != rootVal).toArray();
        } else {
            return Arrays.stream(inorder).dropWhile(ele -> ele != rootVal).filter(e -> e != rootVal).toArray();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,1,2,4;1,2,3,4;3,1,4,null,2",
            "3,9,20,15,7;9,3,15,20,7;3,9,20,null,null,15,7",
            "1,2,3;3,2,1;1,2,null,3,null",
            "3,2,1;3,2,1;3,null,2,null,1",
            "1;1;1;"
    })
    void test(String preStr, String inStr, String expectedTreeStr){
        TreeNode expectedTree = TreeNode.createTreeFromStringArr(expectedTreeStr);
        int[] preOrder = Arrays.stream(preStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] inOrder = Arrays.stream(inStr.split(",")).mapToInt(Integer::parseInt).toArray();
        assertEquals(expectedTree, buildTree(preOrder, inOrder));
//        Assert.assertEquals(Arrays.toString(new int[]{4,5,6}),Arrays.toString(findInOrderSubArray(new int[]{1,2,3,4,5,6}, 3, false)));
//        Assert.assertEquals(Arrays.toString(new int[]{1,2}),Arrays.toString(findInOrderSubArray(new int[]{1,2,3,4,5,6}, 3, true)));
    }
    }
