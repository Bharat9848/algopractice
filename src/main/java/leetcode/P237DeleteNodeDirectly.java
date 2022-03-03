package leetcode;

import core.ds.ListNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *Write a function to delete a node in a singly-linked list. You will not be given access to the head of the list, instead you will be given access to the node to be deleted directly.
 *
 * It is guaranteed that the node to be deleted is not a tail node in the list.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Input: head = [4,5,1,9], node = 1
 * Output: [4,5,9]
 * Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function.
 * Constraints:
 *
 *     The number of the nodes in the given list is in the range [2, 1000].
 *     -1000 <= Node.val <= 1000
 *     The value of each node in the list is unique.
 *     The node to be deleted is in the list and is not a tail node
 */
class P237DeleteNodeDirectly {
    void deleteNode(ListNode node) {
        ListNode temp = node;
        while (temp!= null){
            temp.val = temp.next.val;
            if(temp.next.next == null){
                temp.next = null;
            }
            temp = temp.next;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,5,1,9|5|4,1,9",
            "4,5,1,9|1|4,5,9"
    })
    void test(String listStr, int nodeVal, String expectedListStr){
        ListNode head = ListNode.createListFromStr(listStr);
        ListNode expected = ListNode.createListFromStr(expectedListStr);
        ListNode temp = head;
        while (temp != null){
            if(temp.val == nodeVal){
                break;
            }
            temp = temp.next;
        }
        deleteNode(temp);
        Assertions.assertEquals(expected, head);
    }
}
