package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import util.Pair;

/**
 * Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
 *
 * You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.
 *
 * We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.
 *
 * Input: root = [4,2,5,1,3]
 * Output: [1,2,3,4,5]
 *
 * Explanation: The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line means the predecessor relationship.
 *
 * Example 2:
 *
 * Input: root = [2,1,3]
 * Output: [1,2,3]
 * Constraints:
 *
 *     The number of nodes in the tree is in the range [0, 2000].
 *     -1000 <= Node.val <= 1000
 *     All the values of the tree are unique.
 */
public class P426BSTToDoublyLinkedList {
    private class Node {
         int val;
         Node left;
         Node right;

     Node() {}

     Node(int _val) {
            val = _val;
        }

     Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    Node treeToDoublyList(Node root) {
            Pair<Node, Node> firstAndLastNode = convertToDoublyLinkedList(root);
            Node first = firstAndLastNode.getFirst();
            Node last = firstAndLastNode.getSec();
            first.left = last;
            last.right = first;
            return first;
        }

    private Pair<Node, Node> convertToDoublyLinkedList(Node root) {
        if(root.left == null && root.right == null){
            return new Pair<>(root, root);
        }
        Node leftMinSubNode = root;
        if(root.left != null){
            Pair<Node, Node> firstLastLeftSubTree = convertToDoublyLinkedList(root.left);
            leftMinSubNode = firstLastLeftSubTree.getFirst();
            root.left = firstLastLeftSubTree.getSec();
            firstLastLeftSubTree.getSec().right = root;
        }
        Node rightMaxSubNode = root;
        if(root.right != null){
            Pair<Node, Node> firstLastRightSubTree = convertToDoublyLinkedList(root.right);
            rightMaxSubNode = firstLastRightSubTree.getSec();
            root.right = firstLastRightSubTree.getFirst();
            firstLastRightSubTree.getFirst().left = root;
        }

        return new Pair<>(leftMinSubNode, rightMaxSubNode);
    }

    @Test
    void test(){
        Node root = new Node(2, new Node(1), new Node(3));
        Node actual = treeToDoublyList(root);
        Assert.assertEquals(1, actual.val);
        Assert.assertEquals(3, actual.left.val);
        Assert.assertEquals(2, actual.right.val);
        Assert.assertEquals(1, actual.right.left.val);
        Assert.assertEquals(3, actual.right.right.val);
        Assert.assertEquals(2, actual.right.right.left.val);
        Assert.assertEquals(1, actual.right.right.right.val);
        Assert.assertEquals(3, actual.left.val);
    }

    @Test
    void test2(){
//        4,2,5,1,3
        Node root = new Node(4, new Node(2, new Node(1), new Node(3)), new Node(5));
        Node actual = treeToDoublyList(root);
        Node current = actual;
        Node prev = actual.left;
        Node next = actual.right;
        Assert.assertEquals(5, actual.left.val);
        int count =0;
        while (current != actual.left){
            count++;
            Assert.assertEquals(count, current.val);
            Assert.assertEquals(prev.val, current.left.val);
            Assert.assertEquals(next.val, current.right.val);
            prev = current;
            current = current.right;
            next = current.right;
        }
        Assert.assertEquals(++count, current.val);
    }
    @Test
    void test3(){
//        4,2,5,1,3
        Node root = new Node(4, new Node(3, new Node(2, new Node(1), null), null), null);
        Node actual = treeToDoublyList(root);
        Node current = actual;
        int count =0;
        while (current != actual.left){
            count++;
            Assert.assertEquals(count, current.val);
            current = current.right;
        }
        Assert.assertEquals(++count, current.val);

    }
    @Test
    void test4(){
//        4,2,5,1,3
        Node root = new Node(1, null, new Node(2, null, new Node(3, null,new Node(4))));
        Node actual = treeToDoublyList(root);
        Node current = actual;
        int count =0;
        while (current != actual.left){
            count++;
            Assert.assertEquals(count, current.val);
            current = current.right;
        }
        Assert.assertEquals(++count, current.val);

    }
}
