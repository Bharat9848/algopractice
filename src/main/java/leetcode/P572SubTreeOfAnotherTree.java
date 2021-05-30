package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static core.ds.TreeNode.createTreeFromStringArr;

/*
Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.

A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants. The tree tree could also be considered as a subtree of itself.

Example 1:

Input: root = [3,4,5,1,2], subRoot = [4,1,2]
Output: true

Example 2:

Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
Output: false


Constraints:

    The number of nodes in the root tree is in the range [1, 2000].
    The number of nodes in the subRoot tree is in the range [1, 1000].
    -104 <= root.val <= 104
    -104 <= subRoot.val <= 104
 */
public class P572SubTreeOfAnotherTree {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root==null && subRoot == null){
            return true;
        }
        if(root == null) return false;
        if(subRoot == null) return false;
        List<TreeNode> subtreeOpt = findSubTreeRoot(root, subRoot.val);
        return subtreeOpt.stream().map(subRootFromMain -> isSame(subRootFromMain, subRoot))
                .reduce(false, (prev, next) -> prev||next);
    }

    private Boolean isSame(TreeNode subRootFromMain, TreeNode subRoot) {
        if(subRootFromMain == null && subRoot == null) return true;
        if(subRootFromMain == null) return false;
        if(subRoot == null) return false;
        return (subRootFromMain.val.equals(subRoot.val)) && isSame(subRootFromMain.left, subRoot.left) && isSame(subRootFromMain.right, subRoot.right);
    }

    private List<TreeNode> findSubTreeRoot(TreeNode root, int val) {
        if (root == null) return new ArrayList<>();
        List<TreeNode> result;
        if (root.val == val) {
            result = new ArrayList<>() {{
                add(root);
            }};
        } else {
            result = new ArrayList<>();
        }
        result.addAll(findSubTreeRoot(root.left, val));
        result.addAll(findSubTreeRoot(root.right, val));
        return result;
    }


    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,4,5,1,2,null,null,null,null,0;4,1,2;false",
            "3,4,5,1,2;4,1,2;true",
            "1;1;true",
            "3,2,1;1;true",
            "3,2,1;4;false",
            "1,1;1;true",
            "1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,null,1,2;1,null,1,null,1,null,1,null,1,null,1,2; true"
            })
    void testIsSubTree(String mainTree, String subTree, boolean expected){
        TreeNode treeFromStringArr = createTreeFromStringArr(mainTree);
//        Assert.assertEquals(Optional.of(treeFromStringArr.left), findSubTreeRoot(treeFromStringArr.left, treeFromStringArr.left.val));
        Assert.assertEquals(expected, isSubtree(treeFromStringArr, createTreeFromStringArr(subTree)));
    }
}
