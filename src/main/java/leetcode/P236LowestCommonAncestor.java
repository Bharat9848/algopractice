package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 *
 * Example 2:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * Output: 5
 * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 *
 * Example 3:
 *
 * Input: root = [1,2], p = 1, q = 2
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [2, 105].
 *     -109 <= Node.val <= 109
 *     All Node.val are unique.
 *     p != q
 *     p and q will exist in the tree.
 *
 *     for each Node find p and find q using dfs if p is on left side and q is on right side or vice versa then node is the answer
 */
class P236LowestCommonAncestor {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> stackP = null, stackQ = null, stack = new LinkedList<>();
        stack.add(root);
        Set<TreeNode> visited = new HashSet<>();
        visited.add(root);
        while(!stack.isEmpty() && (stackP==null || stackQ==null)){
            TreeNode node = stack.get(0);
            if(node.val == p.val){
                stackQ = new ArrayList<>(stack);
            }
            if(node.val == q.val){
                stackP = new ArrayList<>(stack);
            }
            if(node.right !=null && !visited.contains(node.right)){
                visited.add(node.right);
                stack.add(0, node.right);
                continue;
            }
            if(node.left !=null && !visited.contains(node.left)){
                visited.add(node.left);
                stack.add(0, node.left);
                continue;
            }
            stack.remove(0);
        }
        TreeNode common = null;
        if(stackP == null || stackQ == null){
            return null;
        }
        for (int i = stackP.size()-1, j = stackQ.size()-1 ; i >= 0 && j>=0 ; i--, j--) {
            TreeNode treeNodeP = stackP.get(i);
            TreeNode treeNodeQ = stackQ.get(j);
            if(treeNodeP.val == treeNodeQ.val){
                common = treeNodeP;
            }else{
                break;
            }
        }
        return common;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2|1|2|1",
            "3,5,1,6,2,0,8,null,null,7,4|5|4|5",
            "3,5,1,6,2,0,8,null,null,7,4|5|1|3"
    })
    void test(String treeStr, int pVal, int qVal, int expected){
        Assert.assertEquals(Integer.valueOf(expected), lowestCommonAncestor(TreeNode.createTreeFromStringArr(treeStr), new TreeNode(pVal), new TreeNode(qVal)).val);
    }

    @Test
    void testUnHappy(){
        Assert.assertNull(lowestCommonAncestor(TreeNode.createTreeFromStringArr("3,5,1,6,2,0,8,null,null,7,4"), new TreeNode(9), new TreeNode(3)));
    }
}
