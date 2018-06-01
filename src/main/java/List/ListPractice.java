package List;

import core.ds.ListNode;
import org.junit.Test;

import java.util.Objects;

import static core.ds.ListNode.createSeqIntList;

/**
 * Created by bharat on 6/3/18.
 */
public class ListPractice {

    public static <T> ListNode<T> problem1ReverseIterative(ListNode<T> head){
        Objects.nonNull(head);
        ListNode<T> temp1 = null;
        ListNode<T> temp2 = head;
        ListNode<T> temp3;
        while (temp2 != null){
            temp3 = temp2.getNext();
            temp2.setNext(temp1);
            temp1 = temp2;
            temp2 = temp3;
        }
        return temp1;
    }

    public static <T> ListNode<T> problem2ReverseRecursive(ListNode<T> node){
        Objects.nonNull(node);
        if(node.getNext()==null){
            return node;
        }
        ListNode<T> head = problem2ReverseRecursive(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return head;
    }

    @Test
    public void testReverseIterative(){
        ListNode<Integer> n1 = createSeqIntList(5);
        System.out.println(ListNode.print(n1));
        ListNode<Integer> reverseList = ListPractice.problem1ReverseIterative(n1);
        System.out.println(ListNode.print(reverseList));

    }
    @Test
    public void testReverseRecursive(){
        ListNode<Integer> n1 = createSeqIntList(5);
        System.out.println(ListNode.print(n1));
        ListNode<Integer> reverseList = ListPractice.problem2ReverseRecursive(n1);
        System.out.println(ListNode.print(reverseList));

    }

    public static <T> ListNode<T> P3ReverseLinklistInKpairs(ListNode<T> head, int k) {
        ListNode<T> prevLast = null;
        ListNode<T> tempStart = head;
        ListNode<T> tempEnd = head;
        ListNode<T> nextStart;
        ListNode<T> newHead = null;
        while (tempEnd != null) {
            for (int i = 0; i < k-1 && tempEnd != null; i++) {
                tempEnd = tempEnd.getNext();
            }
            if (tempEnd == null) {
                break;
            } else {
                nextStart = tempEnd.getNext();
                tempEnd.setNext(null);
                if (prevLast != null) {
                    prevLast.setNext(null);
                    prevLast.setNext(problem2ReverseRecursive(tempStart));
                }else{
                    newHead = problem2ReverseRecursive(tempStart);
                }
                tempStart.setNext(nextStart);

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
        ListNode<Integer> n1 = createSeqIntList(4);
        System.out.println(ListNode.print(n1));
        ListNode<Integer> reverseList = ListPractice.P3ReverseLinklistInKpairs(n1,9);
        System.out.println(ListNode.print(reverseList));
    }

    public static <T> ListNode<T> swapKthNodeFromStartAndLast(ListNode<T> head, int k){
        ListNode<T> kEndPrev=null,kEndNext,temp,kEnd = head;
        ListNode<T> kstartPrev=head,kStartNext,kStart = head;
        for (int i = 0; i < k - 1 && kStart!=null; i++) {
            kstartPrev = kStart;
            kStart = kStart.getNext();
        }
        if(kStart==null){
            return head;
        }else{
            temp=kStart;
            while(temp.getNext()!=null){
                kEndPrev = kEnd;
                kEnd = kEnd.getNext();
                temp = temp.getNext();
            }
            kEndNext = kEnd.getNext();
            kStartNext = kStart.getNext();

            if(k==1){
                head = kEnd;
            }

            if(kStart==kEnd){
                return head;
            }
            if(kStart==kEndPrev){
                kstartPrev.setNext(null);
                kStart.setNext(null);
                kEnd.setNext(null);
                kstartPrev.setNext(kEnd);
                kEnd.setNext(kStart);
                kStart.setNext(kEndNext);
            }else {
                kEndPrev.setNext(null);

                kEnd.setNext(null);
                if(kstartPrev!=null)
                    kstartPrev.setNext(null);
                kStart.setNext(null);

                if(kstartPrev!=null)
                    kstartPrev.setNext(kEnd);
                kEnd.setNext(kStartNext);

                kEndPrev.setNext(kStart);
                kStart.setNext(kEndNext);
            }
        }
        return head;
    }

    @Test
    public void testSwapKStartAndEnd(){
        ListNode<Integer> n1 = createSeqIntList(10);
        System.out.println(ListNode.print(n1));
        ListNode<Integer> reverseList = ListPractice.swapKthNodeFromStartAndLast(n1,5);
        System.out.println(ListNode.print(reverseList));
    }
}
