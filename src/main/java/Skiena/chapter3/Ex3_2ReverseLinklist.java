package Skiena.chapter3;

import core.ds.ListNode;

/*
Reverse a linked list. It should take O(n) time.
 */
public class Ex3_2ReverseLinklist {

    ListNode reverseList(ListNode node){
        if(node.next == null){
            return node;
        }
        ListNode newHead = reverseList(node.next);
        ListNode nextNode = node.next;
        node.next=(null);
        nextNode.next=(node);
        return newHead;
    }

    public static void main(String[] args){
        ListNode seqList = ListNode.createSeqIntList(5);
        Ex3_2ReverseLinklist algo = new Ex3_2ReverseLinklist();
        System.out.println(ListNode.print(seqList));
        System.out.println(ListNode.print(algo.reverseList(seqList)));
    }
}
