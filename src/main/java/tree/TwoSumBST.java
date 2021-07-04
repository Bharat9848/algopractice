package tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bharat on 1/6/18.
 */
public class TwoSumBST {
    private class Node{
        Node left;
        Node right;
        int value;
    }

    boolean sumK(Node root, int k){
        Iterator<Node> l = new MyIterator(root);
        Iterator<Node> r = new MyRevIterator(root);;
        Node ln = l.next();
        Node rn = r.next();
        while(ln!=rn){

            if(ln.value == k - rn.value){
                return true;
            }else if(ln.value + rn.value>k){
                rn = r.next();
            }else{
                ln = l.next();
            }

        }
        return false;
    }

    class MyIterator implements Iterator<Node>{
        List<Node> l = new ArrayList();
        public MyIterator(Node root) {
            Node temp = root;
            while (temp != null) {
                l.add(0,temp);
                temp = temp.left;
            }
        }

        public boolean hasNext() {
            return !l.isEmpty();
        }


        public Node next() {
            Node p = l.remove(0);
            if (p.right != null) {
                 Node temp = p.right;
                 while(temp!=null){
                     l.add(0,temp);
                     temp = temp.left;
                }
            }
            return p;
        }
}

class MyRevIterator implements Iterator<Node>{
    List<Node> l = new ArrayList<>();
    public MyRevIterator(Node root) {
        Node temp = root;
        while (temp != null) {
            l.add(0,temp);
            temp = temp.right;
        }
    }

    public boolean hasNext() {
        return !l.isEmpty();
    }

    public Node next() {
        Node p = l.remove(0);
        if (p.left != null) {
            Node temp = p.left;
            while(temp!=null){
                l.add(0,temp);
                temp = temp.right;
            }
        }
        return p;
    }
}

    @Test
    public void test(){
        Node n10 = createNode(10);
        Node n5 = createNode(5);
        Node n3 = createNode(3);
        Node n6 = createNode(6);
        n5.left=n3;
        n5.right = n6;
        Node n12 = createNode(12);
        n10.left = n5;
        n10.right = n12;
        Assert.assertTrue(sumK(n10,11));
    }

    private Node createNode(int i) {
        Node node = new Node();
        node.value = i;
        return node;
    }

}
