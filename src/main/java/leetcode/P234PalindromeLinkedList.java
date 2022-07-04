package leetcode;

import core.ds.ListNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;

/**
 * Given the head of a singly linked list, return true if it is a palindrome.
 *
 * Input: head = [1,2,2,1]
 * Output: true
 *
 * Input: head = [1,2]
 * Output: false
 *
 *     The number of nodes in the list is in the range [1, 10^5].
 *     0 <= Node.val <= 9
 *
 * Follow up: Could you do it in O(n) time and O(1) space?
 */
class P234PalindromeLinkedList {

    boolean isPalindrome(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode slow=dummy, fast=dummy;
        StringBuilder halfNo = new StringBuilder("");
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
            if(fast != null){
                fast = fast.next;
                halfNo.append(slow.val);
            }
        }
        StringBuilder secondHalf = new StringBuilder("");
        while (slow != null){
            secondHalf.insert(0, slow.val);
            slow = slow.next;
        }
        return halfNo.toString().equals(secondHalf.toString());
    }
    boolean isPalindromeTLE(ListNode head) {
        String no = "";
        int noOfDigits = 0;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode slow=dummy, fast=dummy;
        while (fast != null){
            slow = slow.next;
            fast = fast.next;
            if(fast != null) {
                noOfDigits++;
                fast = fast.next;
                if(fast != null){
                    noOfDigits++;
                }
            }
            if(fast != null){
                no = no + slow.val;
            }
        }
        System.out.println(no);
        if(noOfDigits%2==1){
            slow = slow.next;
        }
        String otherHalf = "";
        while(slow != null){
            otherHalf =  otherHalf + slow.val;
            slow = slow.next;
        }
        boolean isPalindrome = true;
        for (int i = 0; i < otherHalf.length() && isPalindrome; i++) {
            isPalindrome = otherHalf.charAt(i) == no.charAt(no.length()-1-i);
        }
        return isPalindrome;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "6,5,1,7,7,9,3,8,1,5,7,7,8,4,0,9,3,7,3,4,5,7,4,8,8,5,8,9,8,5,8,8,4,7,5,4,3,7,3,9,0,4,8,7,7,5,1,8,3,9,7,7,1,5,6|true",
            "1,2,2,1|true",
            "1,2,2,2,1|true",
            "1,2|false",
            "1|true",
            "3,4,1,2,2,1,4,3|true",
    })
    void test(String listStr, boolean expected){
        Assertions.assertEquals(expected, isPalindrome(ListNode.createListFromStr(listStr)));
    }
}
