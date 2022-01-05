package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given the root of a binary tree, collect a tree's nodes as if you were doing this:
 *
 *     Collect all the leaf nodes.
 *     Remove all the leaf nodes.
 *     Repeat until the tree is empty.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,2,3,4,5]
 * Output: [[4,5,3],[2],[1]]
 * Explanation:
 * [[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.
 *
 * Example 2:
 *
 * Input: root = [1]
 * Output: [[1]]
 *
 * @TODO do it by measuring heights
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [1, 100].
 *     -100 <= Node.val <= 100
 */
 class P366FindLeavesOfBinaryTree {

     List<List<Integer>> findLeaves(TreeNode root) {
         Map<TreeNode, TreeNode> parentChildMap = new HashMap<>();
         List<List<Integer>> result = new ArrayList<>();
         parentChildMap.put(root, root);
         LinkedHashSet<TreeNode> leaves = findLeaves(root, parentChildMap);
         Set<TreeNode> removedLeaves = new HashSet<>();
         while(!leaves.isEmpty()){
             LinkedHashSet<TreeNode> parentNodes = new LinkedHashSet<>();
             List<Integer> subResult = new ArrayList<>();
             removedLeaves.addAll(leaves);
             Iterator<TreeNode> leavesIterator = leaves.iterator();
             while (leavesIterator.hasNext()){
                TreeNode child = leavesIterator.next();
                subResult.add(child.val);
                if(parentChildMap.containsKey(child)  && !child.equals(parentChildMap.get(child))){
                    TreeNode parent = parentChildMap.get(child);
                    if((parent.left == null ||  removedLeaves.contains(parent.left)) && (parent.right == null ||  removedLeaves.contains(parent.right))){
                        parentNodes.add(parent);
                    }
                }
             }
             result.add(subResult);
             leaves = parentNodes;
         }
        return result;
    }

    private LinkedHashSet<TreeNode> findLeaves(TreeNode node, Map<TreeNode, TreeNode> parentChildMap) {
        if(node.left == null && node.right == null){
            return new LinkedHashSet<>(){{add(node);}};

        }
        LinkedHashSet<TreeNode> leftLeaves = new LinkedHashSet<>();
        if(node.left != null){
            parentChildMap.put(node.left, node);
            leftLeaves = findLeaves(node.left, parentChildMap);
        }
        LinkedHashSet<TreeNode> rightLeaves = new LinkedHashSet<>();
        if(node.right != null){
            parentChildMap.put(node.right, node);
            rightLeaves = findLeaves(node.right, parentChildMap);
        }
        leftLeaves.addAll(rightLeaves);
        return leftLeaves;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4,5|[[4,5,3],[2],[1]]",
            "1|[1]"
    })
    void test(String treeStr, String expectedStr){
         TreeNode root = TreeNode.createTreeFromStringArr(treeStr);
         List<List<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                 .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                 .filter(s -> !s.isEmpty())
                 .map(s -> Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, findLeaves(root));
    }
}
