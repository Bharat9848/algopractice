package tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * We are given the root of a binary tree with nn nodes and an array, queries, of size mm. Each query represents the root of a subtree that should be removed from the tree. The task here is to determine the height of the binary tree after each query, i.e., once a subtree is removed. We'll store the updated heights against each query in an array and return it.
 *
 *     Note: A tree’s height is the number of edges in the longest path from the root to any leaf node in the tree.
 *
 * A few points to be considered:
 *
 *     All the values in the tree are unique.
 *
 *     It is guaranteed that queries[i] will not be equal to the value of the root.
 *
 *     The queries are independent, so the tree returns to its initial state after each query.
 *
 * Constraints:
 *
 *     2≤2≤ nn ≤500≤500
 *
 *     1≤1≤ Node.data ≤≤ nn
 *
 *     mm == queries.length
 *
 *     1≤1≤ mm ≤min(≤min(nn, 400)400)
 *
 *     1≤1≤ queries[i] ≤≤ nn
 *
 *     queries[i] ≠= root.data
 */
public class HeightBinaryTreeAfterNodeRemoval {
    private  class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    public int[] heightsAfterQueries(TreeNode<Integer> root, int[] queries) {
        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            result[i] = heightAfterNodeRemoval(root, queries[i]);
        }
        // Replace this placeholder return statement with your code
        return result;
    }
    private int heightAfterNodeRemoval(TreeNode<Integer> root, int nodeVal){
        int currentheight = 0;
        int heightTillNode=0;
        int maxHeightInNodePath=0;
        int maxHeightInOtherPath=0;
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.add(root);
        Map<TreeNode<Integer>, Boolean> visited = new HashMap<>();
        boolean nodeInStack = false;
        while (!stack.empty()){
            TreeNode<Integer> current = stack.peek();
            visited.put(current, true);
            if(current.data == nodeVal){
                nodeInStack = true;
                heightTillNode = currentheight;
            }
            if(current.left ==  null && current.right == null) {
                if(nodeInStack){
                    maxHeightInNodePath = Math.max(currentheight, maxHeightInNodePath);
                } else {
                    maxHeightInOtherPath = Math.max(currentheight, maxHeightInOtherPath);
                }
            } else {
                if(current.left != null && !visited.containsKey(current.left)){
                    currentheight +=1;
                    stack.push(current.left);
                    continue;
                }
                if(current.right != null && !visited.containsKey(current.right)){
                    currentheight +=1;
                    stack.push(current.right);
                    continue;
                }
            }

            if(current.data == nodeVal){
                nodeInStack = false;
            }
            currentheight -= 1;
            stack.pop();
        }


        if(maxHeightInOtherPath >= maxHeightInNodePath){
            return maxHeightInOtherPath;
        } else if(maxHeightInOtherPath >= heightTillNode - 1){
            return maxHeightInOtherPath;
        } else {
            return heightTillNode - 1;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "3,2,17,1,4,19,5|19,17,2,1|2,2,2,2",
            "1,2,3|2,3|1,1",
            "1,2,3,null,null,4,5|2,3|2,1"
    })
    void test(String treeStr, String queriesStr, String expectedStr){
        int[] queries = Arrays.stream(queriesStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, heightsAfterQueries(createTreeNodeRoot(treeStr.split(",")), queries));
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
