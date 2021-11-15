package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).
 * Example 1:
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[20,9],[15,7]]
 *
 * Example 2:
 *
 * Input: root = [1]
 * Output: [[1]]
 *
 * Example 3:
 *
 * Input: root = []
 * Output: []

 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 2000].
 *     -100 <= Node.val <= 100
 */
public class P103BinaryTreeZigzagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return new ArrayList<>();
        }else {
            List<TreeNode> parentLevel = new ArrayList<>(1);
            parentLevel.add(root);
            boolean leftToRight = true;
            while (!parentLevel.isEmpty()){
                List<Integer> level = new ArrayList<>();
                List<TreeNode> nextLevel = new LinkedList<>();
                while (!parentLevel.isEmpty()){
                    int index;
                    if(leftToRight){ index=0; }else{index = parentLevel.size()-1;}
                    TreeNode node = parentLevel.remove(index);
                    level.add(node.val);
                    if(leftToRight){
                        if(node.left != null){
                            nextLevel.add(node.left);
                        }
                        if(node.right != null){
                            nextLevel.add(node.right);
                        }

                    }else{
                        //@TODO bug
                        if(node.right != null){
                            nextLevel.add(0, node.right);
                        }
                        if(node.left != null){
                            nextLevel.add(0, node.left);
                        }
                    }
                }
                leftToRight = !leftToRight;
                parentLevel = nextLevel;
                result.add(level);
            }
            return result;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|[[1]]",
            "1,2,3|[[1],[3,2]]",
            "3,9,20,null,null,15,7|[[3],[20,9],[15,7]]",
            "1,2,3,4,null,null,5|[[1],[3,2],[4,5]]"
    })
    void test(String treeStr,String resultStr){
        List<List<Integer>> expected = Arrays.stream(resultStr.split("],\\["))
                .map(str -> str.replaceAll("\\[", "").replaceAll("]", "").trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
                .collect(Collectors.toList());
        Assert.assertEquals(expected, zigzagLevelOrder(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
