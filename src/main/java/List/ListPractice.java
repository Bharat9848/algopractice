package List;

import core.ds.ListNode;
import org.junit.Test;

import java.util.Objects;

import static core.ds.ListNode.createSeqIntList;

/**
 * Created by bharat on 6/3/18.
 */
public class ListPractice {

    public static  ListNode problem1ReverseIterative(ListNode head){
        Objects.nonNull(head);
        ListNode temp1 = null;
        ListNode temp2 = head;
        ListNode temp3;
        while (temp2 != null){
            temp3 = temp2.next;
            temp2.next = temp1;
            temp1 = temp2;
            temp2 = temp3;
        }
        return temp1;
    }

    public static  ListNode problem2ReverseRecursive(ListNode node){
        Objects.nonNull(node);
        if(node.next ==null){
            return node;
        }
        ListNode head = problem2ReverseRecursive(node.next);
        node.next.next = node;
        node.next = null;
        return head;
    }

    @Test
    public void testReverseIterative(){
        ListNode n1 = createSeqIntList(5);
        System.out.println(ListNode.print(n1));
        ListNode reverseList = ListPractice.problem1ReverseIterative(n1);
        System.out.println(ListNode.print(reverseList));

    }
    @Test
    public void testReverseRecursive(){
        ListNode n1 = createSeqIntList(5);
        System.out.println(ListNode.print(n1));
        ListNode reverseList = ListPractice.problem2ReverseRecursive(n1);
        System.out.println(ListNode.print(reverseList));

    }

    public static  ListNode P3ReverseLinklistInKpairs(ListNode head, int k) {
        ListNode prevLast = null;
        ListNode tempStart = head;
        ListNode tempEnd = head;
        ListNode nextStart;
        ListNode newHead = null;
        while (tempEnd != null) {
            for (int i = 0; i < k-1 && tempEnd != null; i++) {
                tempEnd = tempEnd.next;
            }
            if (tempEnd == null) {
                break;
            } else {
                nextStart = tempEnd.next;
                tempEnd.next = (null);
                if (prevLast != null) {
                    prevLast.next = (null);
                    prevLast.next = (problem2ReverseRecursive(tempStart));
                }else{
                    newHead = problem2ReverseRecursive(tempStart);
                }
                tempStart.next = (nextStart);

                prevLast = tempStart;
                tempStart = nextStart;
                tempEnd = tempStart;
            }
        }
        if(newHead!=null){
            return newHead;
        }else{
            return head;
        }
    }

    @Test
    public void testReverseLinklistInKPairs(){
        ListNode n1 = createSeqIntList(4);
        System.out.println(ListNode.print(n1));
        ListNode reverseList = ListPractice.P3ReverseLinklistInKpairs(n1,9);
        System.out.println(ListNode.print(reverseList));
    }

    public static  ListNode swapKthNodeFromStartAndLast(ListNode head, int k){
        ListNode kEndPrev=null,kEndNext,temp,kEnd = head;
        ListNode kstartPrev=head,kStartNext,kStart = head;
        for (int i = 0; i < k - 1 && kStart!=null; i++) {
            kstartPrev = kStart;
            kStart = kStart.next;
        }
        if(kStart==null){
            return head;
        }else{
            temp=kStart;
            while(temp.next!=null){
                kEndPrev = kEnd;
                kEnd = kEnd.next;
                temp = temp.next;
            }
            kEndNext = kEnd.next;
            kStartNext = kStart.next;

            if(k==1){
                head = kEnd;
            }

            if(kStart==kEnd){
                return head;
            }
            if(kStart==kEndPrev){
                kstartPrev.next=(null);
                kStart.next=(null);
                kEnd.next=(null);
                kstartPrev.next=(kEnd);
                kEnd.next=(kStart);
                kStart.next=(kEndNext);
            }else {
                kEndPrev.next=(null);

                kEnd.next=(null);
                if(kstartPrev!=null)
                    kstartPrev.next=(null);
                kStart.next=(null);

                if(kstartPrev!=null)
                    kstartPrev.next=(kEnd);
                kEnd.next=(kStartNext);

                kEndPrev.next=(kStart);
                kStart.next=(kEndNext);
            }
        }
        return head;
    }

    @Test
    public void testSwapKStartAndEnd(){
        ListNode n1 = createSeqIntList(10);
        System.out.println(ListNode.print(n1));
        ListNode reverseList = ListPractice.swapKthNodeFromStartAndLast(n1,5);
        System.out.println(ListNode.print(reverseList));
    }
}
