package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Given the head of a linked list, rotate the list to the right by k places.
Example 1:

Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]

Example 2:

Input: head = [0,1,2], k = 4
Output: [2,0,1]

 Constraints:

    The number of nodes in the list is in the range [0, 500].
    -100 <= Node.val <= 100
    0 <= k <= 2 * 109
(k+i)%size==0 break the list bring it in front

 */
public class P61RotateList {
    private class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    ListNode rotateRight(ListNode head, int k) {
        int size = 0;
        ListNode temp = head;
        while (temp!=null){
            size++;
            temp = temp.next;
        }
        if(head==null||size ==0 ||k==0 || size==1 ){return head;}
        System.out.println("size = "+ size);
        int index = 0;
        ListNode prev = null, curr=head;
        while(curr != null){
            if((index+k)%size == 0){
                System.out.println("breaking out at index = "+ index);
                break;
            }
            index++;
            prev = curr;
            curr = curr.next;
        }
        if(prev != null) {
            prev.next = null;
            ListNode newHead = curr;
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = head;
            return newHead;
        }else{
            return head;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
//            "1,2,3,4,5;2;4,5,1,2,3",
//            "0,1,2;4;2,0,1",
            "1,2;2;1,2"// 012-> 201 -> 120 -> 012 -> 201
    })
    void test(String inputList, int k, String expected){
        ListNode listNode = Arrays.stream(inputList.split(","))
                .map(val -> new ListNode(Integer.parseInt(val)))
                .reduce((prev, next) -> {
                    ListNode temp = prev;
                    while (temp.next != null){
                        temp = temp.next;
                    }
                    temp.next = next;
                    return prev;
                }).get();
        ListNode result = rotateRight(listNode, k);
        String resultStr = Stream.iterate(result, l -> l != null, l -> l.next).map(x -> x.val + "").collect(Collectors.joining(","));
        Assert.assertEquals(expected, resultStr);
    }

}
