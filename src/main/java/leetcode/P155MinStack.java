package leetcode;

import org.junit.Test;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

 push(x) -- Push element x onto stack.
 pop() -- Removes the element on top of the stack.
 top() -- Get the top element.
 getMin() -- Retrieve the minimum element in the stack.
 */
public class P155MinStack {

    private final List<Pair<Integer, Integer>> stack;

    public P155MinStack() {
        stack = new ArrayList<>();
    }

    public void push(int x) {

        int lastMin = stack.size()>0?stack.get(0).getSec():Integer.MAX_VALUE;
        if(lastMin>x){
            stack.add(0,new Pair<>(x,x));
        }else{
            stack.add(0,new Pair<>(x,lastMin));
        }

    }

    public void pop() {
        if (stack.size() > 0) {
            stack.remove(0);
        } else {
            throw new RuntimeException("Stack is Empty");
        }
    }

    public int top() {
        if(stack.size()>0){
            return stack.get(0).getFirst();
        }else {
            throw new RuntimeException("Stack is Empty");
        }
    }

    public int getMin() {
        if(stack.size()>0){
            return stack.get(0).getSec();
        }else {
            throw new RuntimeException("Stack is Empty");
        }
    }

    @Test
    public void test123(){
//        [,"getMin","pop","push","top","getMin","push","top","getMin","pop","getMin"]
//[,[],[],[],[],[2147483647],[],[],[-2147483648],[],[],[],[]]
        P155MinStack stack = new P155MinStack();
        stack.push(2147483646);
        stack.push(2147483646);
        stack.push(2147483647);
        assertEquals(2147483647,stack.top());
        stack.pop();
        assertEquals(2147483646,stack.getMin());
        stack.pop();
        assertEquals(2147483646,stack.getMin());
        stack.pop();
        stack.push(2147483647);
        assertEquals(2147483647,stack.top());
        assertEquals(2147483647,stack.getMin());
        stack.push(-2147483648);
        assertEquals(-2147483648,stack.getMin());
        assertEquals(-2147483648,stack.top());
        stack.pop();
        assertEquals(2147483647,stack.getMin());

    }
}
