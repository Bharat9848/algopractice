package leetcode;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given the root of a binary tree, return the maximum width of the given tree.
 * <p>
 * The maximum width of a tree is the maximum width among all levels.
 * <p>
 * The width of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes are also counted into the length calculation.
 * <p>
 * It is guaranteed that the answer will in the range of 32-bit signed integer.
 * <p>
 * Input: root = [1,3,2,5,3,null,9]
 * Output: 4
 * Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
 * <p>
 * Example 2:
 * <p>
 * Input: root = [1,3,null,5,3]
 * Output: 2
 * Explanation: The maximum width existing in the third level with the length 2 (5,3).
 * <p>
 * Example 3:
 * <p>
 * Input: root = [1,3,2,5]
 * Output: 2
 * Explanation: The maximum width existing in the second level with the length 2 (3,2).
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the tree is in the range [1, 3000].
 * -100 <= Node.val <= 100
 *
 * @TODO Number the nodes Also try with DFS
 *
 */
class P662MaximumDepthOfBinaryTreeTLE {

    int widthOfBinaryTree(TreeNode root) {
        int lastLevelWidth = 1;

        TreeNode left = root, right = root;
        while(!isLeaf(left) || !isLeaf(right)){
            int possibleNextLevelWidth = 2 * lastLevelWidth;
            if(left.left == null){
                possibleNextLevelWidth--;
            }
            if(right.right == null){
                possibleNextLevelWidth--;
            }
            lastLevelWidth = possibleNextLevelWidth;
        }
        return lastLevelWidth;
    }

    private boolean isLeaf(TreeNode node) {
        return node.right == null && node.left == null;
    }

    int widthOfBinaryTreeTLE(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int width=1;
        Deque<TreeNode> levelNodes = new ArrayDeque<>();
        levelNodes.add(root);
        while (!levelNodes.isEmpty()){
            Deque<TreeNode> subLevelNodes = new ArrayDeque<>();
            while (!levelNodes.isEmpty()){
                TreeNode current = levelNodes.remove();
                subLevelNodes.add(current.left != null ? current.left : new TreeNode(101));
                subLevelNodes.add(current.right != null ? current.right : new TreeNode(101));
            }
            trimNullLeaves(subLevelNodes);
            width = Math.max(width, subLevelNodes.size());
            levelNodes = subLevelNodes;
        }
        return width;
    }

    private void trimNullLeaves(Deque<TreeNode> subLevelNodes) {
       while (!subLevelNodes.isEmpty()){
            if(subLevelNodes.peekFirst().val == 101){
                subLevelNodes.removeFirst();
            }else{
                break;
            }
        }
       while (!subLevelNodes.isEmpty()){
           if(subLevelNodes.peekLast().val == 101){
               subLevelNodes.removeLast();
           }else {
               break;
           }
       }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,2,5,3,null,9|4",
            "1,3,null,5,null|1",
            "1,3,null,5,3|2",
            "1,3,2,5|2"
    })
    void test(String treeStr, int expected) {
        Assertions.assertEquals(expected, widthOfBinaryTree(TreeNode.createTreeFromStringArr(treeStr)));
    }
}
