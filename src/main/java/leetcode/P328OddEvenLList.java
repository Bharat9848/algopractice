package leetcode;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
328. Odd Even Linked List

Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.

You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

Example 1:

Input: 1->2->3->4->5->NULL
Output: 1->3->5->2->4->NULL
Example 2:

Input: 2->1->3->5->6->4->7->NULL
Output: 2->3->6->7->1->5->4->NULL
Note:

The relative order inside both the even and odd groups should remain as it was in the input.
The first node is considered odd, the second node even and so on ...
 */
public class P328OddEvenLList {
    private class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode sec = head.next, even = head.next;
        ListNode odd = head;
        while(even != null){
            odd.next = even.next;
            odd = even.next == null? odd : even.next;
            even.next = odd.next;
            even = odd.next;
        }
        odd.next = sec;
        return head;
    }

    @Test
    public void testOddEven(){
        List<ListNode> arg = IntStream.range(1,2).mapToObj(i -> new ListNode(i)).collect(Collectors.toList());
        ListNode a = arg.stream().reduce(new ListNode(0),(x,y)-> {
            ListNode temp = x;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next =y;
            return x;
        });
        ListNode res = oddEvenList(a);
        while (res!=null){
            System.out.print(res.val);
            res = res.next;
        }
    }
}
