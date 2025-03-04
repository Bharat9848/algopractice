package stack.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * Given a string, s, that may have matched and unmatched parentheses, remove the minimum number of parentheses so that the resulting string represents a valid parenthesization. For example, the string “a(b)” represents a valid parenthesization while the string “a(b” doesn’t, since the opening parenthesis doesn’t have any corresponding closing parenthesis.
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤103≤103
 *     s[i] is either an opening parenthesis (( , a closing parenthesiss )), or a lowercase English letter.
 */
public class MinParenthesisRemoval {
    public static String minRemoveParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if(chars[i] == ')' && (stack.empty() || chars[stack.peek()] == ')')){
                stack.push(i);
            } else if (chars[i] == ')' && (!stack.empty() && chars[stack.peek()] == '(')) {
                stack.pop();
            } else if (chars[i] == '('){
                stack.push(i);
            }
        }
        char[] result = new char[s.length() - stack.size()];
        int index = result.length -1;
        for (int i = chars.length - 1; i >= 0 ; i--) {
            if(!stack.empty() && i == stack.peek()){
                stack.pop();
            } else {
                result[index] = chars[i];
                index--;
            }
        }
        return new String(result);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "((a)b|(a)b",
            "(((abc)(to)((q)()(|(abc)(to)(q)()",
            "()|()",
    })
    void test(String s, String expected){
        Assertions.assertEquals(expected, minRemoveParentheses(s));
    }
}
