package leetcode;

import com.sun.source.tree.Tree;
import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given the root of a complete binary tree, return the number of the nodes in the tree.
 *
 * According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
 *
 * Design an algorithm that runs in less than O(n) time complexity.
 * Input: root = [1,2,3,4,5,6]
 * Output: 6
 * Input: root = []
 * Output: 0
 * Input: root = [1]
 * Output: 1
 *
 *     The number of nodes in the tree is in the range [0, 5 * 104].
 *     0 <= Node.val <= 5 * 104
 *     The tree is guaranteed to be complete.
 *
 * @TODO solution with less complexity is binary search at last level
 */
class P222CountNodesCompleteTree {
    int countNodes(TreeNode root) {
        if(root==null){
            return 0;
        }
        Queue<TreeNode> level = new LinkedList<>();
        level.add(root);
        int count = 1;
        while (!level.isEmpty()){
            Queue<TreeNode> subLevel = new LinkedList<>();
            while (!level.isEmpty()){
                TreeNode current = level.remove();
                if(current.left != null){
                    subLevel.add(current.left);
                    count++;
                }else{
                    break;
                }
                if(current.right != null){
                    subLevel.add(current.right);
                    count++;
                }else{
                    break;
                }
                level = subLevel;
            }
        }
        return count;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"1,2,3,4,5,6|6",
            "|0",
            "1|1"
    })
    void test(String treeStr, int expected){
        Assertions.assertEquals(expected, countNodes(treeStr!=null?TreeNode.createTreeFromStringArr(treeStr):null));
    }
}
