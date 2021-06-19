package tree;

import core.ds.TreeNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
Two nodes in a binary tree can be called cousins if they are on the same level of the tree but have different parents. For example, in the following diagram 4 and 6 are cousins.

    1
   / \
  2   3
 / \   \
4   5   6

Given a binary tree and a particular node, find all cousins of that node.

    1.Need level order traversal
    2. before adding to queue search if the node is the particaular node.
        if true then then do not add the node as well as its parents other children in the queue
        if false then add all the nodes in the queue
    3. after #2 with its true condition is reached then all the other nodes in the queues are cousin.
 */
public class BinaryTreeCousin {
    List<Integer> cousinOfNode(TreeNode root, TreeNode node) {
        if (root.val.equals(node.val)) return new ArrayList<>();
        List<TreeNode> level = new LinkedList<>();
        level.add(root);
        boolean nodeMatched = false;
        List<TreeNode> nextLevel = new LinkedList<>();
        while (!level.isEmpty()) {
            TreeNode current = level.remove(0);
            if ((current.left != null && node.val.equals(current.left.val)) || (current.right != null && node.val.equals(current.right.val))) {
                nodeMatched = true;
            } else {
                if (current.right != null) {
                    nextLevel.add(current.right);
                }
                if (current.left != null) {
                    nextLevel.add(current.left);
                }
            }
            if (level.isEmpty() && !nodeMatched) {
                level = nextLevel;
                nextLevel = new LinkedList<>();
            } else if (level.isEmpty() && nodeMatched) {
                level = nextLevel;
                break;
            }
        }
        return level.stream().map(treeNode -> treeNode.val).collect(Collectors.toList());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4,5,6;4;6",
            "1,2,3,4,5,6;2;"

    })
    void test(String treeStr, int nodeVal, String cousinListStr) {
        List<Integer> expected = cousinListStr==null?new ArrayList<>():Arrays.stream(cousinListStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Assert.assertEquals(expected, cousinOfNode(TreeNode.createTreeFromStringArr(treeStr), new TreeNode(nodeVal)));

    }
}
