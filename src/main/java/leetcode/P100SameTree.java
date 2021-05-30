package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static core.ds.TreeNode.createTreeFromStringArr;

/*
Given the roots of two binary trees p and q, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

Example 1:

Input: p = [1,2,3], q = [1,2,3]
Output: true

Example 2:

Input: p = [1,2], q = [1,null,2]
Output: false

Example 3:

Input: p = [1,2,1], q = [1,1,2]
Output: false

 Constraints:

    The number of nodes in both trees is in the range [0, 100].
    -104 <= Node.val <= 104


 */
public class P100SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null) return false;
        if(q == null) return false;
        return (p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3;1,2,3;true",
            "1,2;1,null,2;false",
            "1,2,1;1,1,2;false"
    })
    void testIsSameTree(String firstTree, String secondTree, boolean expected){
        Assert.assertEquals(expected, isSameTree(createTreeFromStringArr(firstTree), createTreeFromStringArr(secondTree)));
    }
}
