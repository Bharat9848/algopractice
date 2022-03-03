package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (push, peek, pop, and empty).
 *
 * Implement the MyQueue class:
 *
 *     void push(int x) Pushes element x to the back of the queue.
 *     int pop() Removes the element from the front of the queue and returns it.
 *     int peek() Returns the element at the front of the queue.
 *     boolean empty() Returns true if the queue is empty, false otherwise.
 *
 * Notes:
 *
 *     You must use only standard operations of a stack, which means only push to top, peek/pop from top, size, and is empty operations are valid.
 *     Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.
 *
 * Input
 * ["MyQueue", "push", "push", "peek", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 1, 1, false]
 *
 * Explanation
 * MyQueue myQueue = new MyQueue();
 * myQueue.push(1); // queue is: [1]
 * myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
 * myQueue.peek(); // return 1
 * myQueue.pop(); // return 1, queue is [2]
 * myQueue.empty(); // return false
 *
 *     1 <= x <= 9
 *     At most 100 calls will be made to push, pop, peek, and empty.
 *     All the calls to pop and peek are valid.
 *
 * Follow-up: Can you implement the queue such that each operation is amortized O(1) time complexity? In other words, performing n operations will take overall O(n) time even if one of those operations may take longer.
 */
class P232ImplementQueueUsingTwoStack {
    Stack<Integer> main, helper;
    P232ImplementQueueUsingTwoStack(){
        main = new Stack<>();
        helper = new Stack<>();
    }

    public void push(int x) {
        while (!main.isEmpty()){
            helper.push(main.pop());
        }
        main.push(x);
        while (!helper.isEmpty()){
            main.push(helper.pop());
        }
    }

    public int pop() {
        return main.pop();
    }

    public int peek() {
        return main.peek();
    }

    public boolean empty() {
        return main.isEmpty();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MyQueue,push,push,peek,pop,empty|,1,2,,,|null,null,null,1,1,false",
    })
    void test(String operationsStr, String argStr, String expectedStr){
        P232ImplementQueueUsingTwoStack queue = null;
        String[] operations = operationsStr.split(",");
        String[] arguments = argStr.split(",");
        String[] expectation = expectedStr.split(",");
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            switch (operation) {
                case "MyQueue": {
                    queue = new P232ImplementQueueUsingTwoStack();
                    break;
                }
                case "push": {
                    queue.push(Integer.parseInt(arguments[i]));
                    break;
                }
                case "pop": {
                    Assertions.assertEquals(Integer.parseInt(expectation[i]), queue.pop());
                    break;
                }
                case "peek": {
                    Assertions.assertEquals(Integer.parseInt(expectation[i]), queue.peek());
                    break;
                }
                case "empty": {
                    Assertions.assertEquals(Boolean.parseBoolean(expectation[i]), queue.empty());
                }

            }

        }
    }
}
