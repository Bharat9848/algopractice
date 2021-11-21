package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.
 *
 *
 *
 * Example 1:
 *
 * Input: l1 = [1,2,4], l2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 *
 * Example 2:
 *
 * Input: l1 = [], l2 = []
 * Output: []
 *
 * Example 3:
 *
 * Input: l1 = [], l2 = [0]
 * Output: [0]
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in both lists is in the range [0, 50].
 *     -100 <= Node.val <= 100
 *     Both l1 and l2 are sorted in non-decreasing order.
 */

class P21MergeTwoSortedList {
    ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        ListNode dummy = new ListNode(Integer.MAX_VALUE);
        ListNode curr1=l1, curr2= l2, resultTail=dummy;
        while (curr1 != null && curr2 != null){
            if (curr1.val <= curr2.val) {
                resultTail.next = new ListNode(curr1.val);
                curr1 = curr1.next;

            } else {
                resultTail.next = new ListNode(curr2.val);
                curr2 = curr2.next;
            }
            resultTail = resultTail.next;
        }
        ListNode rest = null;
        if(curr1!= null){
            rest = curr1;
        }
        if(curr2 != null){
            rest = curr2;
        }
        while (rest != null){
            resultTail.next = new ListNode(rest.val);
            rest = rest.next;
            resultTail = resultTail.next;
        }
        return dummy.next;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
         "1,2,4|1,3,4|1,1,2,3,4,4",
         "1,2,4|1,3,4,5,6|1,1,2,3,4,4,5,6",
         "1,2,4,5,6|1,3,4|1,1,2,3,4,4,5,6",
         "1,2,3||1,2,3",
         "|1,2,3|1,2,3"
    })
    void test(String list1Str, String list2Str, String expectedStr){
        Assert.assertEquals(ListNode.createListFromStr(expectedStr), mergeTwoLists(ListNode.createListFromStr(list1Str), ListNode.createListFromStr(list2Str)));
    }
}
