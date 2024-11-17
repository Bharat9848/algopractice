package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * P1853
 * Good morning! Here's your coding interview problem for today.
 *
 * This problem was asked by Amazon.
 *
 * Implement a stack that has the following methods:
 *
 *     push(val), which pushes an element onto the stack
 *     pop(), which pops off and returns the topmost element of the stack. If there are no elements in the stack, then it should throw an error or return null.
 *     max(), which returns the maximum value in the stack currently. If there are no elements in the stack, then it should throw an error or return null.
 *
 * Each method should run in constant time.
 */
public class MaxStack {

    private int[] stack = null;
    private int[] maxStack = null;
    private int top = -1;
    private int maxTop = -1;
    public MaxStack(int size){
        stack = new int[size];
        maxStack = new int[size];
    }
    void push(int val) {
        insertInMaxStack(val);
        top++;
        stack[top] = val;
    }

    private void insertInMaxStack(int val) {
        if(maxTop == -1){
            maxStack[0] = val;
            maxTop = 0;
        }else {
            if(maxStack[maxTop] <= val){
                maxTop++;
                maxStack[maxTop] = val;
            }
        }
    }

    int max(){
     if(maxTop == -1){
         throw new RuntimeException("illegal state. stack is empy");
     }
     return maxStack[maxTop];
    }

    int pop(){
        if(stack[top] == maxStack[maxTop]){
            maxTop--;
        }
        int temp = stack[top];
        top--;
        return temp;
    }


}
