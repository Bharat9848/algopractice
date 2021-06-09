package core.ds;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by bharat on 6/3/18.
 */
public class ListNode {
    public ListNode next;
    public int value;
    public ListNode(int val){
        this.value = val;
    }

    public static ListNode createListFromStr(String commaSepStr) {
        if(commaSepStr==null || commaSepStr.trim().isEmpty()) {return null;}
        return Arrays.stream(commaSepStr.split(","))
                .filter(str -> !str.isEmpty())
                .map(s -> new ListNode(Integer.parseInt(s)))
                .reduce((all,ele)-> {
                    ListNode curr = all;
                    while(curr.next != null){
                        curr = curr.next;
                    }
                    curr.next = ele;
                    return all;
                }).orElseThrow();
    }

    public static ListNode newListNode(int val){
        return new ListNode(val);
    }

    public static String print(final ListNode head){
        ListNode temp = head;
        StringBuilder str = new StringBuilder("");
        while (temp!=null){
            str.append(temp.value).append("====>");
            temp = temp.next;
        }
        str.append("null");
        return str.toString();
    }

    public static ListNode createSeqIntList(int n) {
        ListNode n1 = ListNode.newListNode(1);
        ListNode temp = n1;
        for (int i = 2; i <=n ; i++) {
            ListNode newNode = ListNode.newListNode(i);
            temp.next = newNode;
            temp = temp.next;
        }
        return n1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return value == listNode.value &&
                Objects.equals(next, listNode.next);
    }

    @Override
    public String toString(){
        return print(this);
    }
    @Override
    public int hashCode() {
        return Objects.hash(next, value);
    }
}
