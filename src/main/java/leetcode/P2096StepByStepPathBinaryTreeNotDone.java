package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.*;

/**
 * You are given the root of a binary tree with n nodes. Each node is uniquely assigned a value from 1 to n. You are also given an integer startValue representing the value of the start node s, and a different integer destValue representing the value of the destination node t.
 *
 * Find the shortest path starting from node s and ending at node t. Generate step-by-step directions of such path as a string consisting of only the uppercase letters 'L', 'R', and 'U'. Each letter indicates a specific direction:
 *
 *     'L' means to go from a node to its left child node.
 *     'R' means to go from a node to its right child node.
 *     'U' means to go from a node to its parent node.
 *
 * Return the step-by-step directions of the shortest path from node s to node t.
 *
 * Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
 * Output: "UURL"
 * Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.
 *
 * Input: root = [2,1], startValue = 2, destValue = 1
 * Output: "L"
 * Explanation: The shortest path is: 2 → 1.
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is n.
 *     2 <= n <= 10^5
 *     1 <= Node.val <= n
 *     All the values in the tree are unique.
 *     1 <= startValue, destValue <= n
 *     startValue != destValue
 */
public class P2096StepByStepPathBinaryTreeNotDone {
    public String getDirectionsTLE(TreeNode root, int startValue, int destValue) {
        Map<TreeNode, TreeNode> childToParentMap = createChildToParentMap(root);
        Queue<Pair<TreeNode, String>> nodeToPath = new LinkedList<>();
        nodeToPath.add(new Pair<>(findNode(root, startValue), ""));
        Set<Integer> visited = new HashSet<>();
        while (!nodeToPath.isEmpty()){
            Pair<TreeNode, String> nodePath = nodeToPath.remove();
            String path = nodePath.getSec();
            TreeNode current = nodePath.getFirst();
            visited.add(current.val);
            if(current.val == destValue){
                return path;
            }
            TreeNode parent = childToParentMap.get(current);
            if(parent.val != -1 && !visited.contains(parent.val)){
                nodeToPath.add(new Pair<>(parent, path+"U"));
            }
            if(current.left != null && !visited.contains(current.left.val)){
                nodeToPath.add(new Pair<>(current.left, path + "L"));
            }
            if(current.right != null && !visited.contains(current.right.val)){
                nodeToPath.add(new Pair<>(current.right, path + "R"));
            }
        }
        throw new RuntimeException("No Path found");
    }

    private TreeNode findNode(TreeNode root, int startValue) {
        if(root == null){
            return null;
        }
        if(root.val == startValue){
            return root;
        }
        TreeNode left = findNode(root.left, startValue);
        if(left == null){
            return findNode(root.right, startValue);
        }else {
            return left;
        }
    }

    private Map<TreeNode, TreeNode> createChildToParentMap(TreeNode root) {
        Map<TreeNode, TreeNode> childToParentMap = new HashMap<>();
        childToParentMap.put(root, new TreeNode(-1));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode current = queue.remove();
            if(current.left != null) {
                childToParentMap.put(current.left, current);
                queue.add(current.left);
            }
            if(current.right  != null){
                childToParentMap.put(current.right, current);
                queue.add(current.right);
            }
        }
        return childToParentMap;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5,1,2,3,null,6,4|3|6|UURL",
            "5,1,2,3,null,6,4|2|1|UL",
            "5,1,2,3,null,6,4|1|2|UR",
            "2,1|2|1|L"
    })
    void test(String tree, int start, int dest, String expected){
        Assertions.assertEquals(expected, getDirectionsTLE(TreeNode.createTreeFromStringArr(tree), start, dest));
    }
}
