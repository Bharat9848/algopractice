package leetcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/*
23. Merge k Sorted Lists
Hard

Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6


 */
public class P23MergeKSortingList {

      private class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }

          public ListNode(int val, ListNode next) {
              this.val = val;
              this.next = next;
          }

          @Override
          public boolean equals(Object other){
              if(! (other instanceof ListNode)){
                  return false;
              }
              ListNode otherLs = (ListNode) other;
              ListNode temp = this;
              while(temp!=null && otherLs !=null && temp.val == otherLs.val){
                  temp = temp.next;
                  otherLs = otherLs.next;
              }
               return (temp == null && otherLs == null);
          }
      }

      ListNode mergeKLists(ListNode[] lists) {
          ListNode result = null;
          if(lists.length == 0) return result;
          if(lists.length == 1) return lists[0];
          Pair<ListNode[], PriorityQueue<ListNode>> remainingListAndHeapPair = createHeapFromFirstElemFromEachList(lists);
          boolean stillLeft = Arrays.stream(remainingListAndHeapPair.first).anyMatch(Objects::nonNull);
          PriorityQueue<ListNode> heap  = remainingListAndHeapPair.getSec();
          ListNode[] remaining = remainingListAndHeapPair.getFirst();
          while(stillLeft){
              ListNode min = heap.poll();
              result = appendListNode(result, min);
              Pair<ListNode, ListNode[]> minToRemaining = findMin(remaining);
              remaining = minToRemaining.getSec();
              heap.add(minToRemaining.first);
              stillLeft = Arrays.stream(remaining).anyMatch(Objects::nonNull);
          }
          while (!heap.isEmpty()){
              ListNode nextMin = heap.poll();
              result = appendListNode(result, nextMin);
          }
          return result;
    }

    private Pair<ListNode, ListNode[]> findMin(ListNode[] remaining) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < remaining.length; i++) {
            if(remaining[i] != null){
                if(min > remaining[i].val){
                    minIndex = i;
                    min = remaining[i].val;
                }
            }
        }
        ListNode minLs =  remaining[minIndex];
        remaining[minIndex] = minLs.next;
        minLs.next = null;
        return new Pair<>(minLs, remaining);
    }

    private ListNode appendListNode(ListNode head, ListNode nextMin) {
          if(head == null) return nextMin;
          ListNode temp = head;
          while(temp.next != null){
              temp = temp.next;
          }
          temp.next = nextMin;
          return head;
    }

    private Pair<ListNode[], PriorityQueue<ListNode>> createHeapFromFirstElemFromEachList(ListNode[] lists) {
        ListNode[] heap = new ListNode[lists.length];

        Supplier<Pair<ListNode[], PriorityQueue<ListNode>>> supplier = () -> new Pair<ListNode[],PriorityQueue<ListNode>>(lists,new PriorityQueue<ListNode>(lists.length, Comparator.comparing(ls -> ls.val)));

        BiConsumer<Pair<ListNode[], PriorityQueue<ListNode>>, Integer> accumulator = (pair, index) -> {
            ListNode head = lists[index];
            if(head != null){
                lists[index] = head.next;
                head.next = null;
                pair.getSec().add(head);
            }
        };
        BiConsumer<Pair<ListNode[], PriorityQueue<ListNode>>, Pair<ListNode[], PriorityQueue<ListNode>>> combiner = (a1, a2) -> {};
        return IntStream.range(0, lists.length).boxed().collect(supplier, accumulator, combiner);
    }

    class Pair<K,V>{
        final K first;
        final V sec;

        public Pair(K first, V sec) {
            this.first = first;
            this.sec = sec;
        }

        public K getFirst() {
            return first;
        }

        public V getSec() {
            return sec;
        }

        public String toString(){
            return "("+first+ ", " + sec +")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (!first.equals(pair.first)) return false;
            return sec.equals(pair.sec);
        }

        @Override
        public int hashCode() {
            int result = first.hashCode();
            result = 31 * result + sec.hashCode();
            return result;
        }
    }

    @Test
    public void testsSimple(){
        assertEquals(mergeKLists(new ListNode[0]), null);
        assertEquals(mergeKLists(new ListNode[]{new ListNode(3, new ListNode(6))}), new ListNode(3, new ListNode(6)));
        assertEquals(mergeKLists(new ListNode[]{new ListNode(3), new ListNode(6)}), new ListNode(3, new ListNode(6)));
        assertEquals(mergeKLists(new ListNode[]{new ListNode(3, new ListNode(9)), new ListNode(6)}), new ListNode(3, new ListNode(6, new ListNode(9))));
    }
}
