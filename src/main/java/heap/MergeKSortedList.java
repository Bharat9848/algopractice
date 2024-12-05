package heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Given an array of k sorted linked lists, your task is to merge them into a single sorted linked list and return the head of this linked list.
 *
 * Constraints:
 *
 *     k=k= lists.length
 *     0≤k≤1030≤k≤103
 *     0≤0≤ lists[i].length ≤500≤500
 *     −103≤−103≤ lists[i][j] ≤103≤103
 *     Each lists[i] is sorted in ascending order.
 *     The sum of all lists[i].length will not exceed 103103.
 */
public class MergeKSortedList {
    public static LinkedListNode mergeKLists(List<LinkedList> lists) {
        if(lists == null || lists.isEmpty()){
            return null;
        }
        int size = lists.size();
        var minHeap = new PriorityQueue<Integer>(size, Comparator.comparingInt(a -> a));
        LinkedListNode head = null, last = null;
        while (lists.size() > 1 ){

            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            for (int i = 0; i < lists.size(); i++) {
                var current = lists.get(i).head;
                if(current == null){
                    lists.remove(i);
                    continue;
                }
                if(min > current.data){
                    min = current.data;
                    minIndex = i;
                }
            }

            var nextMinList  = lists.get(minIndex);
            var toAdd = nextMinList.head;
            var nextEle =  nextMinList.head.next;
            nextMinList.head.next = null;
            if(nextEle != null){
                var newList = createNewLinkedList(nextEle);
                lists.remove(nextMinList);
                lists.add(minIndex, newList);
            }else {
                lists.remove(nextMinList);
            }
            if(minHeap.size() == lists.size()){
                if(head == null){
                    int value = minHeap.remove();
                    head = new LinkedListNode(value);
                    last = head;
                }else {
                    last.next = new LinkedListNode(minHeap.remove());
                    last = last.next;
                }
            }
            minHeap.offer(toAdd.data);

        }
        while (!minHeap.isEmpty()) {
            int smallest = minHeap.remove();
            if(head == null){
                head = new LinkedListNode(smallest);
                last = head;
            }else {
                last.next =  new LinkedListNode(smallest);
                last =  last.next;
            }
        }
        if(lists.size() == 1){
            if(head == null){
                return lists.get(0).head;
            } else {
                last.next = lists.get(0).head;
            }
        }
        // Replace this placeholder return statement with your code
        return head;
    }

    private static LinkedList createNewLinkedList(LinkedListNode nextEle) {

        List<Integer> list = new ArrayList<>();
        LinkedListNode temp = nextEle;
        while (temp != null){
            list.add(temp.data);
            temp = temp.next;
        }
        LinkedList lst = new LinkedList();
        lst.createLinkedList(list);
        return lst;
    }

    private static class LinkedListNode {
        public int data;
        public LinkedListNode next;
        // LinkedListNode() will be used to make a LinkedListNode type object.
        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }
    // Template for the linked list
    private static class LinkedList {
        public LinkedListNode head;
        // LinkedList() will be used to make a LinkedList type object.
        public LinkedList() {
            this.head = null;
        }
        // insert_node_at_head method will insert a LinkedListNode at head
        // of a linked list.
        public void insertNodeAtHead(LinkedListNode node) {
            if (this.head == null) {
                this.head = node;
            } else {
                node.next = this.head;
                this.head = node;
            }
        }
        // create_linked_list method will create the linked list using the
        // given integer array with the help of InsertAthead method.
        public void createLinkedList(List<Integer> lst) {
            for (int i = lst.size() - 1; i >= 0; i--) {
                LinkedListNode newNode = new LinkedListNode(lst.get(i));
                insertNodeAtHead(newNode);
            }
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',value = {
            "[[1,3,4]]|1,3,4",
            "[[1],[3],[4]]|1,3,4",
            "[[1,2],[2,4]]|1,2,2,4",
            "[[3,6,9],[8,16,24],[12,23],[1],[1,1,3]]|1,1,1,3,3,6,8,9,12,16,23,24",
            "[[1,2,3,4]]|1,2,3,4",
    })
            void test(String ksortedStr, String expectedStr){
        var pattern = Pattern.compile("\\[(\\d+,)*\\d+\\]");
        var matcher = pattern.matcher(ksortedStr);
        int[][] matrix = matcher.results().map(m -> matcher.group()).map(str -> Arrays.stream(str.replace("]", "").replace("[", "").split(",")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        List<LinkedList> lists = new ArrayList<>(matrix.length);
        int size = 0;
        for (int i = 0; i < matrix.length; i++) {
            LinkedList list =  new LinkedList();
            List<Integer> list1 = Arrays.stream(matrix[i]).boxed().toList();
            size += list1.size();
            list.createLinkedList(list1);
            lists.add(list);
        }
        var result = mergeKLists(lists);
        int[] sortedResult = new int[size];
        LinkedListNode temp = result;
        for (int i = 0; i < size; i++) {
            sortedResult[i] = temp.data;
            temp = temp.next;
        }
        Assertions.assertArrayEquals(Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray(), sortedResult);
    }


}
