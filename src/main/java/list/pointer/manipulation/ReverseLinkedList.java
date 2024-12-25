package list.pointer.manipulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;
import util.StringParser.LinkedListNode;


/**
 * Given the head of a singly linked list, reverse the linked list and return its updated head.
 *
 * Constraints:
 *
 * Let n be the number of nodes in a linked list.
 *
 *     1≤1≤ n ≤500≤500
 *     −5000≤−5000≤ Node.value ≤5000≤5000
 */

/**
 *
 */
public class ReverseLinkedList {
    public static LinkedListNode reverse(LinkedListNode head) {
        LinkedListNode current=head, prev = null;
        while(current.next != null){
            LinkedListNode next = current.next;
            if(prev == null){
                current.next = null;
            } else {
                current.next = prev;
            }
            prev = current;
            current = next;
        }
        current.next = prev;
        return current;
    }


    public static LinkedListNode reverseRecursive(LinkedListNode head) {

        if(head == null){
            return head;
        }
        if(head.next == null){
            return head;
        } else {
            var newHead = reverseRecursive(head.next);
            head.next.next = head;
            head.next = null;
            return newHead;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2,1,5|5,1,2",
            "1,3|3,1",
            "1|1"
    })
    void test(String nodesStr, String expectedStr){
        var head = StringParser.parseLinkedList(nodesStr);
        var expected = StringParser.parseLinkedList(expectedStr);
        LinkedListNode temp1 = reverse(head), temp2 = expected;
        while (temp1 != null){
            Assertions.assertEquals(temp2.data, temp1.data);
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
    }
}
