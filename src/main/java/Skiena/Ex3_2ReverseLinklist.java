package Skiena;

import core.ds.ListNode;

/*
Reverse a linked list. It should take O(n) time.
 */
public class Ex3_2ReverseLinklist {

    ListNode<Integer> reverseList(ListNode<Integer> node){
        if(node.getNext() == null){
            return node;
        }
        ListNode<Integer> newHead = reverseList(node.getNext());
        ListNode<Integer> nextNode = node.getNext();
        node.setNext(null);
        nextNode.setNext(node);
        return newHead;
    }

    public static void main(String[] args){
        ListNode<Integer> seqList = ListNode.createSeqIntList(5);
        Ex3_2ReverseLinklist algo = new Ex3_2ReverseLinklist();
        System.out.println(ListNode.print(seqList));
        System.out.println(ListNode.print(algo.reverseList(seqList)));
    }
}
