package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.List;

/**
 *G iven the root of a binary tree, return the lowest common ancestor of its deepest leaves.
 *
 * Recall that:
 *
 *     The node of a binary tree is a leaf if and only if it has no children
 *     The depth of the root of the tree is 0. if the depth of a node is d, the depth of each of its children is d + 1.
 *     The lowest common ancestor of a set S of nodes, is the node A with the largest depth such that every node in S is in the subtree with root A.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4]
 * Output: [2,7,4]
 * Explanation: We return the node with value 2, colored in yellow in the diagram.
 * The nodes coloured in blue are the deepest leaf-nodes of the tree.
 * Note that nodes 6, 0, and 8 are also leaf nodes, but the depth of them is 2, but the depth of nodes 7 and 4 is 3.
 *
 * Example 2:
 *
 * Input: root = [1]
 * Output: [1]
 * Explanation: The root is the deepest node in the tree, and it's the lca of itself.
 *
 * Example 3:
 *
 * Input: root = [0,1,3,null,2]
 * Output: [2]
 * Explanation: The deepest leaf node in the tree is 2, the lca of one node is itself.
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree will be in the range [1, 1000].
 *     0 <= Node.val <= 1000
 *     The values of the nodes in the tree are unique.
 *
 * Do BFS to find the deepest leaves of trees
 * after getting set of leaves find common ancestor of them two at a time. and reinsert the found ancestor in leaves and remove those two leaves. do above till only one of the node left in the set.
 */
class P1123LowestCommonAncestorOfDeepestLeaves {

    TreeNode lcaDeepestLeaves(TreeNode root) {
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!isDeepestLevel(queue)){
            List<TreeNode> childNodes = new LinkedList<>();
            while (!queue.isEmpty()){
                TreeNode node = queue.remove(0);
                if(node.left!=null){
                    childNodes.add(node.left);
                }
                if(node.right!=null){
                    childNodes.add(node.right);
                }
            }
            queue = childNodes;
        }
        return findLowestCommonAncestor(root, queue);
    }

    private TreeNode findLowestCommonAncestor(TreeNode root, List<TreeNode> queue) {
        while (queue.size()>1){
            TreeNode node1 = queue.remove(0);
            TreeNode node2 = queue.remove(0);
            TreeNode ancestor = findLowestAncestor(root, node1, node2);
            queue.add(0, ancestor);
        }
        return queue.get(0);
    }

    private TreeNode findLowestAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
        if(node1.val == root.val || root.val == node2.val){
            return root;
        }
        if(root.left == null){
            return findLowestAncestor(root.right, node1, node2);
        }else if(root.right == null){
            return findLowestAncestor(root.left, node1, node2);
        }else {
            boolean isNode1InLeft = findNode(root.left, node1);
            boolean isNode2InLeft = findNode(root.left, node2);
            if(isNode1InLeft && isNode2InLeft){
                return findLowestAncestor(root.left, node1, node2);
            }else  if ((!isNode1InLeft && isNode2InLeft) || (!isNode2InLeft && isNode1InLeft)){
                return root;
            }else {
                return findLowestAncestor(root.right, node1, node2);
            }
        }
    }

    private boolean findNode(TreeNode root, TreeNode node1) {
        if(root.val == node1.val){
            return true;
        }
        boolean found = false;
        if(root.left != null){
            found = found | findNode(root.left, node1);
        }
        if(root.right != null){
            found = found | findNode(root.right, node1);
        }
        return found;
    }

    private boolean isDeepestLevel(List<TreeNode> queue) {
        return queue.stream().allMatch(treeNode -> treeNode.right == null && treeNode.left == null);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,5,1,6,2,0,8,null,null,7,4|2,7,4",
            "3,5,1,6,2,0,8|3,5,1,6,2,0,8",
            "1|1",
            "0,1,3,null,2|2"
    })
    void test(String treeStr, String expectStr){
        Assert.assertEquals(TreeNode.createTreeFromStringArr(expectStr), lcaDeepestLeaves(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
