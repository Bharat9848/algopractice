package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by bharat on 27/5/18.
 */
public class P83RemoveDuplicateSortedLinkList {

    public class ListNode {
   int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode temp= head,tempPrev=null;
        while(temp!=null){
            if(tempPrev!=null && temp!=null && (tempPrev.val^temp.val)==0){
                tempPrev.next = temp.next;
                temp.next = null;
                temp = tempPrev.next;
            }else{
            tempPrev = temp;
            temp = temp.next;}
        }
        return head;
    }

    @Test
    public void test(){
        Assert.assertTrue(listEqual(createList("1->2->3"),deleteDuplicates(createList("1->1->2->2->3"))));
        Assert.assertTrue(listEqual(createList("1->2"),deleteDuplicates(createList("1->1->2"))));
    }

    private boolean listEqual(ListNode list, ListNode listNode) {
        ListNode temp1=list,temp2=listNode;
        boolean match = true;
        while (temp1!=null && temp2!=null){
            if(temp1.val!=temp2.val){
                return false;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
        if((temp1==null && temp2!=null)||(temp1!=null && temp2==null)){return false;}
        return match;
    }

    private ListNode createList(String s) {
        String[] ints = s.split("->");
        ListNode head = new ListNode(Integer.parseInt(ints[0]));
        ListNode temp = head;
        for (int i = 1; i < ints.length; i++) {
            temp.next = new ListNode(Integer.parseInt(ints[i]));
        }
        return head;
    }
}
