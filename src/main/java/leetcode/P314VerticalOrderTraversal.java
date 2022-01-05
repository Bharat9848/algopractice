package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).
 *
 * If two nodes are in the same row and column, the order should be from left to right.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[9],[3,15],[20],[7]]
 *
 * Example 2:
 *
 * Input: root = [3,9,8,4,0,1,7]
 * Output: [[4],[9],[3,0,1],[8],[7]]
 *
 * Example 3:
 *
 * Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
 * Output: [[4],[9,5],[3,0,1],[8,2],[7]]
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 100].
 *     -100 <= Node.val <= 100
 */
class P314VerticalOrderTraversal {
    List<List<Integer>> verticalOrder(TreeNode root) {
        if(root==null){return new ArrayList<>();}
        TreeMap<Integer, List<Integer>> accumulator = new TreeMap<>();
        doBreadthFirstTraversal(root, accumulator);
        return new ArrayList<>(accumulator.values());
    }

    private void doBreadthFirstTraversal(TreeNode root, TreeMap<Integer, List<Integer>> accumulator) {
        List<Pair<Integer, TreeNode>> level = new ArrayList<>();
        level.add(new Pair<>(0, root));
        while (!level.isEmpty()){
            List<Pair<Integer, TreeNode>> subLevel = new ArrayList<>();
            while (!level.isEmpty()){
                Pair<Integer, TreeNode> verticalLevelToNode = level.remove(0);
                Integer xCoordinate = verticalLevelToNode.getFirst();
                TreeNode node = verticalLevelToNode.getSec();

                accumulator.putIfAbsent(xCoordinate, new ArrayList<>());
                accumulator.get(xCoordinate).add(node.val);

                if(node.left != null){
                    subLevel.add(new Pair<>(xCoordinate-1, node.left));
                }

                if(node.right != null){
                    subLevel.add(new Pair<>(xCoordinate+1, node.right));
                }
            }
            level = subLevel;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
          "3,9,8,4,0,1,7|[[4],[9],[3,0,1],[8],[7]]",
            "3,9,20,null,null,15,7|[[9],[3,15],[20],[7]]",
            "3,9,8,4,0,1,7,null,null,null,2,5|[[4],[9,5],[3,0,1],[8,2],[7]]"
    })
    void test(String treeStr, String expectedStr){
        TreeNode root = TreeNode.createTreeFromStringArr(treeStr);
        List<List<Integer>> expected = Arrays.stream(expectedStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.trim().split(",")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
        Assert.assertEquals(expected, verticalOrder(root));
    }
}
