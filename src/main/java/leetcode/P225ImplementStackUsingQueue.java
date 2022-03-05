package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (push, top, pop, and empty).
 *
 * Implement the MyStack class:
 *
 *     void push(int x) Pushes element x to the top of the stack.
 *     int pop() Removes the element on the top of the stack and returns it.
 *     int top() Returns the element on the top of the stack.
 *     boolean empty() Returns true if the stack is empty, false otherwise.
 *
 * Notes:
 *
 *     You must use only standard operations of a queue, which means that only push to back, peek/pop from front, size and is empty operations are valid.
 *     Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.
 *
 *
 * Input
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 2, 2, false]
 *
 * Explanation
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top(); // return 2
 * myStack.pop(); // return 2
 * myStack.empty(); // return False
 * Constraints:
 *
 *     1 <= x <= 9
 *     At most 100 calls will be made to push, pop, top, and empty.
 *     All the calls to pop and top are valid.
 * @TODO Follow-up: Can you implement the stack using only one queue?
 */
public class P225ImplementStackUsingQueue {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public P225ImplementStackUsingQueue() {
         queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int x) {
        if(!queue1.isEmpty()){
            queue1.add(x);
        }else {
            queue2.add(x);
        }
    }

    public int pop() {
        if(queue1.isEmpty()){
            int size = queue2.size();
            for (int i = 0; i < size -1; i++) {
                queue1.add(queue2.remove());
            }
            return queue2.remove();
        }else{
            int size = queue1.size();
            for (int i = 0; i < size -1; i++) {
                queue2.add(queue1.remove());
            }
            return queue1.remove();
        }
    }

    public int top() {
        if(queue1.isEmpty()){
            int size = queue2.size();
            for (int i = 0; i < size-1; i++) {
                queue1.add(queue2.remove());
            }
            int toReturn = queue2.remove();
            queue1.add(toReturn);
            return toReturn;
        }else{
            int size = queue1.size();
            for (int i = 0; i < size -1; i++) {
                queue2.add(queue1.remove());
            }
            int toReturn = queue1.remove();
            queue2.add(toReturn);
            return toReturn;
        }
    }

    public boolean empty() {
        return queue2.isEmpty() && queue1.isEmpty();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MyStack,push,push,push,top|,1,2,3,|null,null,null,null,3",
            "MyStack,push,push,top,pop,empty|,1,2,,,|null,null,null,2,2,false",

            })
    void test(String operationsStr, String argStr, String expectedStr){
        P225ImplementStackUsingQueue myStack = null;
        String[] operations = operationsStr.split(",");
        String[] arguments = argStr.split(",");
        String[] expection = expectedStr.split(",");
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "MyStack": {
                    myStack = new P225ImplementStackUsingQueue();
                    break;
                }
                case "empty": {
                    Assert.assertEquals(Boolean.parseBoolean(expection[i]), myStack.empty());
                    break;
                }
                case "push": {
                    myStack.push(Integer.parseInt(arguments[i]));
                }
                break;
                case "pop": {
                    Assertions.assertEquals(Integer.parseInt(expection[i]), myStack.pop());
                }
                break;
                case "top": {
                    Assertions.assertEquals(Integer.parseInt(expection[i]), myStack.top());
                }
            }

        }
    }
}
