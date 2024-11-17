package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;

/**
 * Given a singly linked list, remove the nthnth node from the end of the list and return its head.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is k.
 * 1≤1≤ k ≤103≤103
 * −103≤−103≤ Node.value ≤103≤103
 * 1≤1≤ n ≤≤ k
 */
public class RemoveNthFromLast {
    public LinkedListNode removeNthLastNode(LinkedListNode head, int n) {
        LinkedListNode first = new LinkedListNode(-1);
        first.next = head;
        LinkedListNode sec;
        if (first.next == null) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            first = first.next;
            if(first == null){
                return head;
            }
        }
        // for create n+1 gap we need to increase first one more
        first = first.next;
        if(first == null){
            LinkedListNode temp = head;
            head = head.next;
            temp.next =  null;
            return head;
        }
        sec = head;
        while (first.next != null) {
            sec = sec.next;
            first = first.next;
        }
        LinkedListNode toDelete = sec.next;
        sec.next = sec.next.next;
        toDelete.next = null;
        return head;
    }

    private class LinkedListNode {
        public int data;
        public LinkedListNode next;

        // Constructor will be used to make a LinkedListNode type object
        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',
            value = {
                    "1,2|2|2",
                    "1,2|3|1,2",
                    "1,2,3,4,5|1|1,2,3,4",
                    "1,2,3,4,5|2|1,2,3,5",
                    "1,2,3,4,5|3|1,2,4,5",
                    "1,2,3,4,5|4|1,3,4,5",
                    "1,2,3,4,5|5|2,3,4,5",
            })
    void test(String listStr, int n, String expectedStr) {
        LinkedListNode list = createList(listStr);
        LinkedListNode returned = removeNthLastNode(list, n);
        LinkedListNode temp = returned;
        String[] expectedVals = expectedStr.split(",");
        int i = 0;
        while (temp != null) {
            Assertions.assertEquals(temp.data, Integer.parseInt(expectedVals[i]));
            temp = temp.next;
            i++;
        }
    }

    private LinkedListNode createList(String listStr) {
        String[] nodeVals = listStr.split(",");
        LinkedListNode head = null, last = null;
        for (int i = 0; i < nodeVals.length; i++) {
            LinkedListNode temp = new LinkedListNode(Integer.parseInt(nodeVals[i]));
            if (head == null) {
                head = temp;
                last = temp;
            } else {
                last.next = temp;
                last = temp;
            }
        }

        return head;
    }
}
