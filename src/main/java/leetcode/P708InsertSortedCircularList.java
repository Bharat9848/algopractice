package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a Circular Linked List node, which is sorted in ascending order, write a function to insert a value insertVal into the list such that it remains a sorted circular list. The given node can be a reference to any single node in the list and may not necessarily be the smallest value in the circular list.
 *
 * If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, the circular list should remain sorted.
 *
 * If the list is empty (i.e., the given node is null), you should create a new single circular list and return the reference to that single node. Otherwise, you should return the originally given node.
 *
 * Input: head =    [3,4,1], insertVal = 2
 * Output: [3,4,1,2]
 * Explanation: In the figure above, there is a sorted circular list of three elements. You are given a reference to the node with value 3, and we need to insert 2 into the list. The new node should be inserted between node 1 and node 3. After the insertion, the list should look like this, and we should still return node 3.
 *
 * Input: head = [], insertVal = 1
 * Output: [1]
 * Explanation: The list is empty (given head is null). We create a new single circular list and return the reference to that single node.
 *
 * Input: head = [1], insertVal = 0
 * Output: [1,0]
 *
 *     The number of nodes in the list is in the range [0, 5 * 104].
 *     -106 <= Node.val, insertVal <= 106
 */
class P708InsertSortedCircularList {
    private static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Node)){
                return false;
            }else {
                Node other =  (Node)obj;
                Node original = this;
                Node temp = original;
                Node tempOther = other;
                boolean matches = tempOther.val == temp.val;
                tempOther = tempOther.next;
                temp = temp.next;
                while (temp != original && matches){
                    matches = tempOther.val == temp.val;
                    tempOther = tempOther.next;
                    temp = temp.next;
                }
                return matches;
            }
        }

        public static Node create(String valCommaSep){
            if(valCommaSep == null || valCommaSep.isEmpty()){
                return null;
            }else {
                Node head = null, temp = null;
                String[] vals = valCommaSep.split(",");
                for (String val: vals){
                    if(head == null){
                        head = new Node(Integer.parseInt(val));
                        temp = head;
                    }else{
                        temp.next = new Node(Integer.parseInt(val));
                        temp = temp.next;
                    }
                }
                temp.next = head;
                return head;
            }
        }
    }


        public Node insert(Node head, int insertVal) {
            if(head == null){
                head = new Node(insertVal);
                head.next = head;
            }else {
                Node prev = head, curr = head.next;
                Node newNode = new Node(insertVal);
                boolean inserted = false;
                while (curr != head) {
                    if (prev.val <= curr.val) {
                        if (insertVal >= prev.val && insertVal <= curr.val) {
                            prev.next = newNode;
                            newNode.next = curr;
                            inserted = true;
                            break;
                        }
                    } else {
                        if (insertVal >= prev.val || insertVal <= curr.val) {
                            prev.next = newNode;
                            newNode.next = curr;
                            inserted = true;
                            break;
                        }
                    }
                    prev = curr;
                    curr = curr.next;
                }
                if(!inserted){
                    prev.next = newNode;
                    newNode.next = curr;
                }
            }
            return head;
        }



    @ParameterizedTest
        @CsvSource(delimiter = '|', value = {
                "3,3,3|0|3,3,3,0",
                "3,4,1|2|3,4,1,2",
                "3,4,1|5|3,4,5,1",
                "3,4,1|0|3,4,0,1",
                "3,3,5|0|3,3,5,0",
                "|1|1",
                "1|0|1,0"
        })
        void test(String listStr, int val, String expectedListStr){
            Node before = Node.create(listStr);
            Node after = Node.create(expectedListStr);
            Assertions.assertEquals(after, insert(before, val));
        }
}
