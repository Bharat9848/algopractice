package leetcode;

import core.ds.ListNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given the head of a singly linked list, return the middle node of the linked list.
 *
 * If there are two middle nodes, return the second middle node.
 * Input: head = [1,2,3,4,5]
 * Output: [3,4,5]
 * Input: head = [1,2,3,4,5,6]
 * Output: [4,5,6]
 *
 *     The number of nodes in the list is in the range [1, 100].
 *     1 <= Node.val <= 100
 */
public class P876MiddleOfLinkedList {
    ListNode middleNode(ListNode head) {
        boolean isOdd = true;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode slower = dummy, faster = dummy;
        while (faster.next != null){
            slower = slower.next;
            if(faster.next.next != null){
                faster = faster.next.next;
                isOdd = false;
            }else{
                faster = faster.next;
                isOdd = true;
            }
        }

        return isOdd? slower: slower.next;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1|1",
            "1,2|2",
            "1,2,3,4,5|3,4,5",
            "1,2,3,4,5,6|4,5,6"
    })
    void test(String listStr, String expectedStr){
        Assertions.assertEquals(ListNode.createListFromStr(expectedStr), middleNode(ListNode.createListFromStr(listStr)));
    }
}
