package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
Example 1:

Input: head = [1,2,3,4]
Output: [2,1,4,3]

Example 2:

Input: head = []
Output: []

Example 3:

Input: head = [1]
Output: [1]



Constraints:

    The number of nodes in the list is in the range [0, 100].
    0 <= Node.val <= 100


 */
public class P24SwapNodesInPair {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode first = head, second, prev = dummy;
        while (first != null && first.next != null){
            second = first.next;
            first.next = first.next.next;
            second.next = first;
            prev.next = second;

            prev = first;
            first = first.next;
        }
        return dummy.next;
    }
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1;1",
            "1,2;2,1",
            "1,2,3;2,1,3",
            "1,2,3,4,5;2,1,4,3,5"
    })
    void test(String inputStr, String expected){
        Assert.assertEquals(ListNode.createListFromStr(expected), swapPairs(ListNode.createListFromStr(inputStr)));
    }

    @Test
    public void testNull(){
        Assert.assertNull(swapPairs(null));
    }
}
