package tree;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.invoke.StringConcatFactory;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Given the root of a binary tree, find the most frequent subtree sum. The subtree sum of a node is the sum of all values under a node, including the node itself.
 *
 * For example, given the following tree:
 *
 *   5
 *  / \
 * 2  -5
 *
 * Return 2 as it occurs twice: once as the left leaf, and once as the sum of 2 + 5 - 5.
 */
public class MostFrequentSubTreeSum {
    int mostFreqSubTreeSum(TreeNode node){
        if(node == null) throw new RuntimeException("TreeNode is required");
        Map<Integer, Integer> sumToFreq = new HashMap<>();
        processMostFreq(node, sumToFreq);
        return sumToFreq.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).orElseThrow();
    }

    private int processMostFreq(TreeNode node, Map<Integer, Integer> sumToFreq) {
        if(node == null){
            return 0;
        }
        int leftSum = processMostFreq(node.left, sumToFreq);
        int rightSum = processMostFreq(node.right, sumToFreq);
        int sum = node.val + leftSum + rightSum;
        sumToFreq.putIfAbsent(sum, 0);
        sumToFreq.put(sum, sumToFreq.get(sum) + 1);
        return sum;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "5,2,-5;2",
            "5,2,-5,-5,-5;-5"
    })
    void test(String treeStr, int expected){
        Assert.assertEquals(expected, mostFreqSubTreeSum(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
