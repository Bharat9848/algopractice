package data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Design a custom stack class, Max Stack, that supports the basic stack operations and can find the maximum element present in the stack.
 *
 * Implement the following methods for Max Stack:
 *
 *     Constructor: This initializes the Max Stack object.
 *
 *     Void Push(int x): This pushes the provided element, x, onto the stack.
 *
 *     Int Pop( ): This removes and returns the element on the top of the stack.
 *
 *     Int Top( ): This retrieves the most recently added element on the top of the stack without removing it.
 *
 *     Int peekMax( ): This retrieves the maximum element in the stack without removing it.
 *
 *     Int popMax( ): This retrieves the maximum element in the stack and removes it. If there is more than one maximum element, remove the most recently added one (the topmost).
 *
 * Constraints:
 *
 *     −1000≤−1000≤ x ≤1000≤1000
 *
 *     A maximum of 100100 calls can be made to Push( ), Pop( ), Top( ), peekMax( ) and popMax( ).
 *
 *     The Pop( ), Top( ), peekMax( ), and popMax( ) methods will always be called on a non-empty stack.
 */
public class MaxStackWithMaxPopNotDone
{
    private class MaxStack{
        MaxStack(){

        }

        void push(int x){
        }

        int pop() {
        return -1;
        }
        int top(){
            return -1;
        }

        int peekMax(){
            return -1;
        }

        int popMax(){
           return -1;
        }

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "MaxStack,push,push,push,peekmax,|,3,2,1,,|,,,,3",
            "MaxStack,push,push,push,peekmax,popmax,peekmax|,3,2,1,,,,|,,,,3,3,2",
    })
    void test(String operStr, String argStr, String expectedStr){
        MaxStack stack = new MaxStack();
        String[] operations = operStr.split(",");
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(s -> s.isEmpty()? -1: Integer.parseInt(s)).toArray();
        int[] arguments = Arrays.stream(argStr.split(",")).mapToInt(s -> s.isEmpty()? -1: Integer.parseInt(s)).toArray();
        for(int i= 1; i < operations.length; i++){
            switch(operations[i]){
                case "push" :
                    stack.push(arguments[i]);
                    break;
                case "pop":
                    Assertions.assertEquals(expected[i],stack.pop());
                    break;
                    case "top":
                        Assertions.assertEquals(expected[i], stack.top());
                    break;
                case "peekmax":
                    Assertions.assertEquals(expected[i], stack.peekMax());
                    break;
                    case "popmax":
                        Assertions.assertEquals(expected[i], stack.popMax());

            }

        }
    }
}



















