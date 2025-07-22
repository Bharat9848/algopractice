package stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * You are given a string, s, containing only uppercase English letters. You can perform operations on this string where, in each operation, you remove any occurrence of the substrings "AB" or "CD" from s.
 *
 * Your task is to return the shortest string length after applying all possible operations.
 *
 *     Note: After each removal, the string joins back together, potentially creating new occurrences of "AB" or "CD" that can also be removed.
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤100≤100
 *
 *     s consists only of uppercase English letters.
 */
public class MinLengthAfterRemoveSubstring {
    public int minLength (String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == 'A' || ch == 'C'){
                stack.push(ch);
            } else if(ch == 'B' || ch == 'D'){
                if(!stack.isEmpty()){
                    char lastChar = stack.peek();
                    if((lastChar == 'A' && ch == 'B') || (lastChar == 'C' && ch == 'D')){
                        stack.pop();
                    } else {
                        stack.push(ch);
                    }
                } else {
                    stack.push(ch);
                }
            } else {
                stack.push(ch);
            }
        }
        // Replace this placeholder return statement with your code
        return stack.size();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "ACDB|0",
            "AACDB|1",
            "ABCBD|3",
    })
    public void test(String s, int expected){
        Assertions.assertEquals(expected, minLength(s));
    }
}
