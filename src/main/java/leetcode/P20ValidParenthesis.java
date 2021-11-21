package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 *     Open brackets must be closed by the same type of brackets.
 *     Open brackets must be closed in the correct order.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 *
 * Input: s = "(]"
 * Output: false
 *
 * Example 4:
 *
 * Input: s = "([)]"
 * Output: false
 *
 * Example 5:
 *
 * Input: s = "{[]}"
 * Output: true

 * Constraints:
 *
 *     1 <= s.length <= 10^4
 *     s consists of parentheses only '()[]{}'.
 */
public class P20ValidParenthesis {
    public boolean isValid(String s) {
        List<Character> stack = new LinkedList<>();
        char[] chars = s.toCharArray();
        boolean valid = true;
        for (int i = 0; i < chars.length && valid; i++) {
            if(chars[i] =='}' || chars[i] == ']' || chars[i]==')'){
                if(stack.size() > 0){
                    Character stackChar = stack.remove(0);
                    valid = closingParenthesis(stackChar).equals(chars[i]);
                }else{
                    valid = false;
                }
            }else{
                stack.add(0, chars[i]);
            }

        }
        //TODO bug faild to check if stack is empty
        return valid && stack.isEmpty();
    }

    private Character closingParenthesis(char openingParen) {
        switch (openingParen){
            case '{' : return '}';
            case '(' : return ')';
            case '[' : return ']';
        }
        throw  new RuntimeException("");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "{[]}|true",
            "([)]|false",
            "(]|false",
            "()[]{}|true",
            "()|true",
            "[|false",
            "]|false"
    })
    void test(String s, boolean expected){
        Assert.assertEquals(expected, isValid(s));
    }
}
