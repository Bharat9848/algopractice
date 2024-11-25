package list.slow.fast.pointer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Given a circular linked list, list, of positive integers, your task is to split it into two circular linked lists. The first circular linked list should contain the first half of the nodes (exactly ⌈list.length / 2⌉ nodes), in the same order they appeared in the original list, while the second circular linked list should include the remaining nodes in the same order.
 *
 * Return an array, answer, of length 2, where:
 *
 *     answer[0] is the circular linked list representing the first half.
 *
 *     answer[1] is the circular linked list representing the second half.
 *
 *     Note: A circular linked list is a standard linked list where the last node points back to the first node.
 *
 * Constraints:
 *
 * Let n be the number of nodes in a linked list.
 *
 *     2 ≤≤n ≤≤103103
 *
 *     00≤≤ Node.data ≤≤105105
 *
 *     LastNode.next = FirstNode where LastNode is the last node of the list and FirstNode is the first one.
 */
public class BreakCircularListInTwoHalves {
    public static LinkedListNode[] splitCircularLinkedList(LinkedListNode head) {
           LinkedListNode first, second, secStart;
           LinkedListNode init = new LinkedListNode(-1);
           init.next = head;
           first = init;
           second = init;
           LinkedListNode secondPrev = null;
           while(second != head && !(second.next == head && second != init)) {
               first = first.next;
               secondPrev = second.next;
               second = second.next.next;
           }

        if(second == head){
            secondPrev.next = first.next;
        } else {
            second.next = first.next;
        }
        secStart = first.next;
        first.next = head;
            // Placeholder for actual implementation
            return new LinkedListNode[]{head, secStart}; // Return two empty lists as placeholders
        }


    private static class LinkedListNode {
        public int data;
        public LinkedListNode next;

        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    @Test
    void testTwoNodes(){
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode sec = new LinkedListNode(2);
        one.next = sec;
        sec.next = one;
        LinkedListNode[]  result = splitCircularLinkedList(one);
        LinkedListNode exp1 = new LinkedListNode(1);
        exp1.next = exp1;
        LinkedListNode exp2 = new LinkedListNode(2);
        exp2.next = exp2;
        Assertions.assertTrue(shouldBeSame( exp1, result[0]));
        Assertions.assertTrue(shouldBeSame( exp2, result[1]));
    }

    @Test
    void testFiveNodes(){
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode sec = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);
        one.next = sec;
        sec.next = three;
        three.next = four;
        four.next = five;
        five.next = one;
        LinkedListNode[]  result = splitCircularLinkedList(one);
        LinkedListNode exp1 = new LinkedListNode(1);
        LinkedListNode exp2 = new LinkedListNode(2);
        LinkedListNode exp3 = new LinkedListNode(3);
        exp1.next = exp2;
        exp2.next = exp3;
        exp3.next = exp1;

        LinkedListNode exp4 = new LinkedListNode(4);
        LinkedListNode exp5 = new LinkedListNode(5);
        exp4.next = exp5;
        exp5.next = exp4;
        Assertions.assertTrue(shouldBeSame( exp1, result[0]));
        Assertions.assertTrue(shouldBeSame( exp4, result[1]));
    }

    @Test
    void testSixNodes(){
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode sec = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);
        LinkedListNode six = new LinkedListNode(6);
        one.next = sec;
        sec.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
        six.next = one;
        LinkedListNode[]  result = splitCircularLinkedList(one);
        LinkedListNode exp1 = new LinkedListNode(1);
        LinkedListNode exp2 = new LinkedListNode(2);
        LinkedListNode exp3 = new LinkedListNode(3);
        exp1.next = exp2;
        exp2.next = exp3;
        exp3.next = exp1;

        LinkedListNode exp4 = new LinkedListNode(4);
        LinkedListNode exp5 = new LinkedListNode(5);
        LinkedListNode exp6 = new LinkedListNode(6);
        exp4.next = exp5;
        exp5.next = exp6;
        exp6.next = exp4;
        Assertions.assertTrue(shouldBeSame( exp1, result[0]));
        Assertions.assertTrue(shouldBeSame( exp4, result[1]));
    }

    private boolean shouldBeSame(LinkedListNode exp1, LinkedListNode actual) {
        if(exp1.next == exp1){
            return actual.next == actual;
        }
        LinkedListNode temp1 = exp1.next, temp2 = actual.next;
        boolean matched = temp1.data == temp2.data;
        while (matched && temp1 != exp1){
            temp1 = temp1.next;
            temp2 = temp2.next;
            matched = temp1.data == temp2.data;
        }
        return matched;
    }
}
