package core.ds;

/**
 * Created by bharat on 6/3/18.
 */
public class ListNode<T> {
    private T value;
    private ListNode<T> next;

    public ListNode(T val){
        this.value = val;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public static<T> ListNode newListNode(T val){
        return new ListNode<T>(val);
    }

    public static<T extends Object> String print(final ListNode<T> head){
        ListNode<T> temp = head;
        StringBuilder str = new StringBuilder("");
        while (temp!=null){
            str.append(temp.getValue().toString()).append("====>");
            temp = temp.getNext();
        }
        str.append("null");
        return str.toString();
    }

    public static ListNode<Integer> createSeqIntList(int n) {
        ListNode<Integer> n1 = ListNode.newListNode(1);
        ListNode<Integer> temp = n1;
        for (int i = 2; i <=n ; i++) {
            ListNode<Integer> newNode = ListNode.newListNode(i);
            temp.setNext(newNode);
            temp = temp.getNext();
        }
        return n1;
    }

}
