package leetcode;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).



Example 1:

Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]

Example 2:

Input: root = [1]
Output: [[1]]

Example 3:

Input: root = []
Output: []



Constraints:

    The number of nodes in the tree is in the range [0, 2000].
    -1000 <= Node.val <= 1000


 */
public class P102LevelOrderTraversalBinaryTree {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root==null){return result;}
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        queue.add(null);
        List<Integer> subList = new ArrayList<>();
        while (!queue.isEmpty()){
            TreeNode curr = queue.remove(0);
            if(curr == null && queue.size() > 0){
                result.add(subList);
                subList = new ArrayList<>();
                queue.add(null);
            }else if(curr != null){
                subList.add(curr.val);
                if(curr.left != null) queue.add(curr.left);
                if(curr.right != null) queue.add(curr.right);
            }
        }
        if(!subList.isEmpty()){result.add(subList);}
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3,9,20,null,null,15,7;[[3],[9,20],[15,7]]",
            "3;[[3]]"
    })
    void test(String tree, String expectedList){
        Pattern pat = Pattern.compile("((\\d,?)+)");
        Matcher matcher = pat.matcher(expectedList);
        List<List<Integer>> expected = new ArrayList<>();
        while (matcher.find()){
            expected.add(Arrays.stream(expectedList.substring(matcher.start(), matcher.end()).split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
        Assert.assertEquals(expected, levelOrder(TreeNode.createTreeFromStringArr(tree)));
    }
}
