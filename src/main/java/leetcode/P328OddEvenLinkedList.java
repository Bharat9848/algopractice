package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static core.ds.ListNode.createListFromStr;
/*
Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.

The first node is considered odd, and the second node is even, and so on.

Note that the relative order inside both the even and odd groups should remain as it was in the input.



Example 1:

Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]

Example 2:

Input: head = [2,1,3,5,6,4,7]
Output: [2,3,6,7,1,5,4]



Constraints:

    The number of nodes in the linked list is in the range [0, 104].
    -106 <= Node.val <= 106

 */
public class P328OddEvenLinkedList {

    public ListNode oddEvenList(ListNode head){
        if(head == null) {return head;}
        ListNode preHead = new ListNode(-1);
        preHead.next = (head);
        ListNode evenList = null, evenLast =  null, curr = head;
        while (curr.next != null){
            ListNode evenNode = curr.next;
            curr.next = curr.next.next;
            evenNode.next = null;
            if(evenList == null){
                evenList = evenNode;
                evenLast = evenNode;
            }else {
                evenLast.next = evenNode;
                evenLast = evenNode;
            }
            if (curr.next != null) {
                curr = curr.next;
            }
        }
         curr.next = evenList;
        return preHead.next;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4,5;1,3,5,2,4",
            "1;1",
            "1,2;1,2",
            "1,2,3;1,3,2",
            "1,2,3,4;1,3,2,4"
    })
    void test(String inputStr, String expectedStr){
        Assert.assertEquals(createListFromStr(expectedStr), oddEvenList(createListFromStr(inputStr)));
    }
}
