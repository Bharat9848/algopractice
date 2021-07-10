package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: head = [1,2,6,3,4,5,6], val = 6
 * Output: [1,2,3,4,5]
 * <p>
 * Example 2:
 * <p>
 * Input: head = [], val = 1
 * Output: []
 * <p>
 * Example 3:
 * <p>
 * Input: head = [7,7,7,7], val = 7
 * Output: []
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is in the range [0, 104].
 * 1 <= Node.val <= 50
 * 0 <= val <= 50
 */
public class P203RemoveLinkedListElement {
    ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev=dummy, curr=head;
        while (curr != null){
            if(curr.val == val){
                if(curr.next == null){
                    prev.next = null;
                }else{
                    prev.next= curr.next;
                }
                curr = prev.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "7,7,7,7,1;7;1",
            "7,7,7,7;7;",
            "1,2,6,3,4,5,6;6;1,2,3,4,5",
            ";1;"
    })
    void test(String beforeStr, int value, String afterStr){
        Assert.assertEquals(ListNode.createListFromStr(afterStr), removeElements(ListNode.createListFromStr(beforeStr), value));
    }
}
