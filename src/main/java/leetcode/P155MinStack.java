package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 *
 *     MinStack() initializes the stack object.
 *     void push(int val) pushes the element val onto the stack.
 *     void pop() removes the element on the top of the stack.
 *     int top() gets the top element of the stack.
 *     int getMin() retrieves the minimum element in the stack.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * Output
 * [null,null,null,null,-3,null,0,-2]
 *
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 *
 *
 */
public class P155MinStack {

    private final List<Pair<Integer, Integer>> stack;

    public P155MinStack() {
        stack = new ArrayList<>();
    }

    public void push(int x) {

        int lastMin = stack.size() > 0 ? stack.get(0).getSec() : Integer.MAX_VALUE;
        if (lastMin > x) {
            stack.add(0, new Pair<>(x, x));
        } else {
            stack.add(0, new Pair<>(x, lastMin));
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
        if (stack.size() > 0) {
            return stack.get(0).getFirst();
        } else {
            throw new RuntimeException("Stack is Empty");
        }
    }

    public int getMin() {
        if (stack.size() > 0) {
            return stack.get(0).getSec();
        } else {
            throw new RuntimeException("Stack is Empty");
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MinStack,push,push,push,getMin,pop,top,getMin|null,-2,0,-3,null,null,null,null|null,null,null,null,-3,null,0,-2"
    })
    public void test(String operationStr, String argumentstr, String expected){
        P155MinStack stack = null;
        String[] operations = operationStr.split(",");
        String[] arguments = argumentstr.split(",");
        String[] expection = expected.split(",");
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "MinStack": {
                    stack = new P155MinStack();
                    break;
                }
                case "push": {
                    stack.push(Integer.parseInt(arguments[i]));
                    break;
                }
                case "top": {
                    Assert.assertEquals(Integer.parseInt(expection[i]), stack.top());
                    break;
                }
                case "pop": {
                    stack.pop();
                    break;
                }
                case "getMin": {
                    Assert.assertEquals(Integer.parseInt(expection[i]), stack.getMin());
                    break;
                }
            }

        }

    }
}
