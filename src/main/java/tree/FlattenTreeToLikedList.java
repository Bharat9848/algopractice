package tree;

import core.ds.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Given the root of a binary tree, the task is to flatten the tree into a linked list using the same TreeNode class. The left child pointer of each node in the linked list should always be NULL, and the right child pointer should point to the next node in the linked list. The nodes in the linked list should be in the same order as that of the preorder traversal of the given binary tree.
 * <p>
 * Constraints:
 * <p>
 * −100≤−100≤ Node.data ≤100≤100.
 * The tree contains nodes in the range [1,500][1,500].
 */
public class FlattenTreeToLikedList {

    // Definiton of a binary tree node class
    private class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public TreeNode<Integer> flattenTree(TreeNode<Integer> root) {
        if (root == null) {
            return null;
        }
        var ret = doFlatten(root);
        return root;
    }

    private FirstAndLastTreeNode<TreeNode<Integer>> doFlatten(TreeNode<Integer> root) {
        if (root.left == null && root.right == null) {
            return new FirstAndLastTreeNode<>(root, root);
        }

        if (root.left != null && root.right != null) {
            TreeNode<Integer> tempRight = root.right;
            var firstAndLastLeft = doFlatten(root.left);
            root.left = null;
            root.right = firstAndLastLeft.first;
            var firstAndLastRight = doFlatten(tempRight);
            firstAndLastLeft.last.right = firstAndLastRight.first;
            return new FirstAndLastTreeNode(root, firstAndLastRight.last);

        } else if (root.left != null) {
            var firstAndLastLeft = doFlatten(root.left);
            root.left = null;
            root.right = firstAndLastLeft.first;
            return new FirstAndLastTreeNode<>(root, firstAndLastLeft.last);
        } else {
            var firstAndLastRight = doFlatten(root.right);
            root.right = firstAndLastRight.first;
            return new FirstAndLastTreeNode<>(root, firstAndLastRight.last);
        }
    }


    private class FirstAndLastTreeNode<T> {
        T first;
        T last;

        public FirstAndLastTreeNode(T first, T last) {
            this.first = first;
            this.last = last;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-1,-2,-5,-3,-4,-6,null|-1,-2,-3,-4,-5,-6",
            "3,2,17,1,4,19,5|3,2,1,4,17,19,5",
            "1,2,3|1,2,3",
            "1,2,3,null,null,4,5|1,2,3,4,5"
    })
    void test(String treeStr, String expected){
        String[] treeNode = treeStr.split(",");
        TreeNode<Integer> root = createTreeNodeRoot(treeNode);
        TreeNode<Integer> newRoot = flattenTree(root);
        TreeNode<Integer> temp = newRoot;
        List<String> nodesInOrder = new ArrayList<>();
        while (temp!=null){
            nodesInOrder.add(temp.data.toString());
            temp = temp.right;
        }
        Assertions.assertEquals(expected, String.join(",", nodesInOrder));
    }

    private TreeNode<Integer> createTreeNodeRoot(String[] treeNode) {
       TreeNode<Integer> root = null;
        List<TreeNode<Integer>> queue = new ArrayList<>();
        root = new TreeNode<>(Integer.parseInt(treeNode[0]));
        queue.add(root);
        var i = 1;
        List<TreeNode<Integer>> nextGen = new ArrayList<>();
        while (!queue.isEmpty() && i < treeNode.length){
            var current = queue.removeFirst();
            if(current == null){
                nextGen.add(null);
                nextGen.add(null);
                i++;
                continue;
            }
            String leftStr = treeNode[i];
            if(!"null".equals(leftStr)){
                var left = new TreeNode<>(Integer.parseInt(leftStr));
                current.left = left;
                nextGen.add(left);
            }else {
                nextGen.add(null);
            }
            i++;

            String rightStr = treeNode[i];
            if(!"null".equals(rightStr)){
                var right = new TreeNode<>(Integer.parseInt(rightStr));
                current.right = right;
                nextGen.add(right);
            }else {
                nextGen.add(null);
            }
            i++;
            if(queue.isEmpty()){
                queue = nextGen;
                nextGen = new ArrayList<>();
            }
        }
    return root;
    }
}








