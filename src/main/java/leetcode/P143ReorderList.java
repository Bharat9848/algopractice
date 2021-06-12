package leetcode;

import core.ds.ListNode;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
ou are given the head of a singly linked-list. The list can be represented as:

L0 → L1 → … → Ln - 1 → Ln

Reorder the list to be on the following form:

L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …

You may not modify the values in the list's nodes. Only nodes themselves may be changed.
Example 1:
Input: head = [1,2,3,4]
Output: [1,4,2,3]
Example 2:
Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]
Constraints:

    The number of nodes in the list is in the range [1, 5 * 104].
    1 <= Node.val <= 1000

 1. reach middle node.
 2. break all the links and add all of them in stack
 3. now start from the head till the end of the list
 4. insert node from the stack in between two nodes

 */
public class P143ReorderList {
    public void reorderList(ListNode head) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode midNode = getMidNode(dummy);
        ListNode stackTemp = midNode.next;
        midNode.next = null;
        List<ListNode> stack = new LinkedList<>();
        while (stackTemp != null){
            ListNode temp = stackTemp;
            stackTemp = stackTemp.next;
            temp.next = null;
            stack.add(0, temp);
        }
        ListNode current = head;
        while(current != null){
            ListNode stackEle = stack.size() > 0 ? stack.remove(0): null;
            ListNode temp = current.next;
            current.next = stackEle;
            if(temp!=null)
              stackEle.next = temp;
            current = temp;
        }
        return;
    }

    private ListNode getMidNode(ListNode dummy) {
        ListNode slow = dummy, fast = dummy;
        while (fast.next !=  null){
            slow = slow.next;
            fast = fast.next.next == null ? fast.next: fast.next.next;
        }
        return slow;
    }

    @ParameterizedTest
    @CsvSource( delimiter = ';', value = {
            "1,2,3,4;1,4,2,3",
            "1;1",
            "1,2,3,4,5;1,5,2,4,3"
    })
    public void test(String actualList, String expectedList){
        ListNode actual = ListNode.createListFromStr(actualList);
        reorderList(actual);
        Assert.assertEquals(ListNode.createListFromStr(expectedList), actual);
    }
}
