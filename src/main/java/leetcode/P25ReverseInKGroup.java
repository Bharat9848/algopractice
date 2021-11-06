package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * You may not alter the values in the list's nodes, only nodes themselves may be changed.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 *
 * Example 2:
 *
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 *
 * Example 3:
 *
 * Input: head = [1,2,3,4,5], k = 1
 * Output: [1,2,3,4,5]
 *
 * Input: head = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 *
 *     The number of nodes in the list is in the range sz.
 *     1 <= sz <= 5000
 *     0 <= Node.val <= 1000
 *     1 <= k <= sz
 *
 *
 * Follow-up: Can you solve the problem in O(1) extra memory space?
 */
public class P25ReverseInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        if(k >= 2 && dummy.next != null){
            ListNode startkPrev = dummy;
            ListNode startk = dummy.next;
            ListNode lastK = findNextKthNode(startk, k);
            while (lastK != null){
               startkPrev.next = null;
               ListNode lastKNext = lastK.next;
               lastK.next = null;

               AbstractMap.SimpleEntry<ListNode, ListNode> revFirstToLast = reverseTill(startk, lastK);

               startkPrev.next = revFirstToLast.getKey();
               revFirstToLast.getValue().next = lastKNext;
               //preparing for next loop
               startk = lastKNext;
               startkPrev = revFirstToLast.getValue();
               lastK = findNextKthNode(startk, k);
            }
        }
        return dummy.next;
    }

    private AbstractMap.SimpleEntry<ListNode, ListNode> reverseTill(ListNode current, ListNode lastK) {
        ListNode first = current;
        ListNode sec = current.next;
        first.next = null;
        while (first != lastK){
            ListNode third = sec.next;
            sec.next = first;

            first = sec;
            sec = third;
        }
        return new AbstractMap.SimpleEntry<>(lastK, current);
    }

    private ListNode findNextKthNode(ListNode current, int k) {
        ListNode result = current;
        int i;
        for (i = 0; i < k-1 && result != null; i++) {
            result = result.next;
        }
        return (i==k-1)?result:null;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4,5;2;2,1,4,3,5",
            "1,2,3,4,5;3;3,2,1,4,5",
            "1;1;1",
            "0,1,2;3;2,1,0",
            "1,2;2;2,1"
    })
    void test(String inputList, int k, String expected){
        ListNode listNode = Arrays.stream(inputList.split(","))
                .map(val -> new ListNode(Integer.parseInt(val)))
                .reduce((prev, next) -> {
                    ListNode temp = prev;
                    while (temp.next != null){
                        temp = temp.next;
                    }
                    temp.next = next;
                    return prev;
                }).get();
        ListNode result = reverseKGroup(listNode, k);
        String resultStr = Stream.iterate(result, l -> l != null, l -> l.next).map(x -> x.val + "").collect(Collectors.joining(","));
        Assert.assertEquals(expected, resultStr);
    }
}
