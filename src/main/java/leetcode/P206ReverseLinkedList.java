package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 * Input: head = [1,2]
 * Output: [2,1]
 *
 * Input: head = []
 * Output: []
 * Constraints:
 *
 *     The number of nodes in the list is the range [0, 5000].
 *     -5000 <= Node.val <= 5000
 */
public class P206ReverseLinkedList {
    ListNode reverseList(ListNode head){
        ListNode dummy = new ListNode(-1);
        dummy.next = reverseListRec(head);
        return dummy.next;
    }

    private ListNode reverseListRec(ListNode head) {
        if(head == null){
            return head;
        }
        if(head.next == null){
            return head;
        }
        //1-2-3 => 3-2
        ListNode newHead = reverseListRec(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    @ParameterizedTest
    @CsvSource(
            delimiter = ';', value = {
              "1,2,3,4,5;5,4,3,2,1",
            "1,2;2,1"
    })
    void test(String list, String reversed){
        Assert.assertEquals(ListNode.createListFromStr(reversed), reverseList(ListNode.createListFromStr(list)));
        Assert.assertNull( reverseList(ListNode.createListFromStr(null)));
    }
}
