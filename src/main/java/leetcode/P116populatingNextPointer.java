package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
 * <p>
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 * <p>
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
 * <p>
 * Initially, all next pointers are set to NULL.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: root = [1,2,3,4,5,6,7]
 * Output: [1,#,2,3,#,4,5,6,7,#]
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 * <p>
 * Example 2:
 * <p>
 * Input: root = []
 * Output: []
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the tree is in the range [0, 212 - 1].
 * -1000 <= Node.val <= 1000
 * Follow-up:
 * <p>
 * You may only use constant extra space.
 * The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
 */
public class P116populatingNextPointer {
    private class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Queue<Node> level = new LinkedList<>();
        level.add(root);
        while (!level.isEmpty()) {
            Node lastNode = null;
            Queue<Node> nextLevel = new LinkedList<>();
            while (!level.isEmpty()){
                Node current = level.remove();
                if (current.left != null) {
                    nextLevel.add(current.left);
                }
                if (current.right != null)
                    nextLevel.add(current.right);
                if (lastNode != null) {
                    lastNode.next = current;
                }
                lastNode = current;
            }
            level = nextLevel;
        }
        return root;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4,5,6,7|1,#,2,3,#,4,5,6,7,#"
    })
    void test(String rootStr, String result){
        List<String> expected = Arrays.stream(result.split(",")).collect(Collectors.toList());
        String[] nodeVals = rootStr.split(",");
        int childIndex = 1;
        Node root = null;
        Map<String, Node> valToNode = new HashMap<>();
        valToNode.putIfAbsent(nodeVals[0], new Node(Integer.parseInt(nodeVals[0])));
        for (int parentIndex = 0; parentIndex < nodeVals.length && childIndex< nodeVals.length; parentIndex++) {
           Node parent = valToNode.get(nodeVals[parentIndex]);
           Node left = new Node(Integer.parseInt(nodeVals[childIndex++]));
           Node right = new Node(Integer.parseInt(nodeVals[childIndex++]));
           parent.left = left;
           parent.right = right;
           valToNode.putIfAbsent(left.val+"", left);
           valToNode.putIfAbsent(right.val+"", right);
            if(root == null){
                root = parent;
            }
        }
        connect(root);
        List<String> actual = new LinkedList<>();
        List<Node> level = new LinkedList<>();
        level.add(root);
        while (!level.isEmpty()){
            List<Node> nextLevel = new LinkedList<>();
            actual.add(level.get(0).val + "");
            while (!level.isEmpty()){
                Node node = level.remove(0);
                if(node.next!= null) {
                    actual.add(node.next.val + "");
                }
                if(node.left != null){
                    nextLevel.add(node.left);
                }
                if(node.right != null){
                    nextLevel.add(node.right);
                }
            }
            actual.add("#");
            level = nextLevel;
        }
        Assertions.assertEquals(expected, actual);
    }

}
