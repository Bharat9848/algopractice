package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 *
 *
 * Example 1:
 *
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Example 2:
 *
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Example 3:
 *
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 *
 *
 * Constraints:
 *
 *     The number of nodes in each linked list is in the range [1, 100].
 *     0 <= Node.val <= 9
 *     It is guaranteed that the list represents a number that does not have leading zeros.
 */
public class P2AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode temp1 =l1,temp2 =l2, newListHead = null, newListLast= null;
        int carry = 0;
        while (temp1 != null || temp2 != null){
            int val = (temp1 == null?0:temp1.val) + (temp2==null?0:temp2.val) + carry;
            carry = 0;
            if(val >= 10){
                carry = 1;
                val = val-10;
            }
            if(newListLast == null){
                newListHead = new ListNode(val);
                newListLast = newListHead;
            }else {
                newListLast.next = new ListNode(val);
                newListLast = newListLast.next;
            }
            if(temp1!=null){
                temp1 = temp1.next;
            }
            if(temp2 != null){
                temp2 = temp2.next;
            }
        }
        if(carry == 1){
            newListLast.next = new ListNode(1);
        }

        return newListHead;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3|1,2,3|2,4,6",
            "9,9,9,9,9,9,9|9,9,9,9|8,9,9,9,0,0,0,1",
            "1|9|0,1"
    })
    void test(String list1Str, String list2Str, String expectedStr){
        Assert.assertEquals(ListNode.createListFromStr(expectedStr), addTwoNumbers(ListNode.createListFromStr(list1Str), ListNode.createListFromStr(list2Str)));
    }
}
