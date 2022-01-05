package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Design a max stack data structure that supports the stack operations and supports finding the stack's maximum element.
 *
 * Implement the MaxStack class:
 *
 *     MaxStack() Initializes the stack object.
 *     void push(int x) Pushes element x onto the stack.
 *     int pop() Removes the element on top of the stack and returns it.
 *     int top() Gets the element on the top of the stack without removing it.
 *     int peekMax() Retrieves the maximum element in the stack without removing it.
 *     int popMax() Retrieves the maximum element in the stack and removes it. If there is more than one maximum element, only remove the top-most one.
 *
 *
 *
 * Example 1:
 *
 * Input
 * ["MaxStack", "push", "push", "push", "top", "popMax", "top", "peekMax", "pop", "top"]
 * [[], [5], [1], [5], [], [], [], [], [], []]
 * Output
 * [null, null, null, null, 5, 5, 1, 5, 1, 5]
 *
 * Explanation
 * MaxStack stk = new MaxStack();
 * stk.push(5);   // [5] the top of the stack and the maximum number is 5.
 * stk.push(1);   // [5, 1] the top of the stack is 1, but the maximum is 5.
 * stk.push(5);   // [5, 1, 5] the top of the stack is 5, which is also the maximum, because it is the top most one.
 * stk.top();     // return 5, [5, 1, 5] the stack did not change.
 * stk.popMax();  // return 5, [5, 1] the stack is changed now, and the top is different from the max.
 * stk.top();     // return 1, [5, 1] the stack did not change.
 * stk.peekMax(); // return 5, [5, 1] the stack did not change.
 * stk.pop();     // return 1, [5] the top of the stack and the max element is now 5.
 * stk.top();     // return 5, [5] the stack did not change.
 *
 *
 *
 * Constraints:
 *
 *     -107 <= x <= 107
 *     At most 104 calls will be made to push, pop, top, peekMax, and popMax.
 *     There will be at least one element in the stack when pop, top, peekMax, or popMax is called.
 *
 *
 * Follow up: Could you come up with a solution that supports O(1) for each top call and O(logn) for each other call?
 */
public class P716MaxStack {
    List<int[]> maxStack = new LinkedList<>();

    public P716MaxStack() {

    }

    public void push(int x) {
        if(maxStack.isEmpty()){
            maxStack.add(0, new int[]{x,x});
        }else{
            int[] top = maxStack.get(0);
            if(top[1] < x){
                maxStack.add(0, new int[]{x, x});
            } else{
                maxStack.add(0, new int[]{x, top[1]});
            }
        }
    }

    public int pop() {
        return maxStack.remove(0)[0];
    }

    public int top() {
        return maxStack.get(0)[0];
    }

    public int peekMax() {
        return maxStack.get(0)[1];
    }

    public int popMax() {
        int max = peekMax();
        List<Integer> eleToReinsert = new LinkedList<>();
        while (!maxStack.isEmpty()){
            int[] ele = maxStack.remove(0);
            if(ele[0] != max){
                eleToReinsert.add(0, ele[0]);
            } else {
              break;
            }
        }
        eleToReinsert.forEach(this::push);
        return max;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MaxStack,push,push,push,top,popMax,top,peekMax,pop,top|null,5,1,5,null,null,null,null,null,null|null,null,null,null,5,5,1,5,1,5",
            "MaxStack,push,push,top|null,5,1,null|null,null,null,1",
            "MaxStack,push,push,popMax,peekMax|null,5,1,null,null|null,null,null,5,1"
    })
    void test(String callStr, String argStr, String expectedStr){
        {
            String[] calls = callStr.split(",");
            String[] args = argStr.split(",");
            String[] expected = expectedStr.split(",");

            P716MaxStack maxStack=null;
            for (int i = 0; i < calls.length ; i++) {
                switch(calls[i]){
                    case "MaxStack" : maxStack = new P716MaxStack();
                        break;
                    case "push": maxStack.push(Integer.parseInt(args[i]));
                        break;
                    case "top": Assert.assertEquals(Integer.parseInt(expected[i]), maxStack.top());
                        break;
                    case "popMax": Assert.assertEquals(Integer.parseInt(expected[i]), maxStack.popMax());
                    break;
                    case "peekMax": Assert.assertEquals(Integer.parseInt(expected[i]), maxStack.peekMax());
                    break;
                    case "pop": Assert.assertEquals(Integer.parseInt(expected[i]), maxStack.pop());
                        break;

                    default: throw new RuntimeException("Invalid input");
                }
            }
        }
    }
}
