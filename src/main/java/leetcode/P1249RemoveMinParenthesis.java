package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given a string s of '(' , ')' and lowercase English characters.
 *
 * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.
 *
 * Formally, a parentheses string is valid if and only if:
 *
 *     It is the empty string, contains only lowercase characters, or
 *     It can be written as AB (A concatenated with B), where A and B are valid strings, or
 *     It can be written as (A), where A is a valid string.
 *
 * Input: s = "lee(t(c)o)de)"
 * Output: "lee(t(c)o)de"
 * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
 *
 * Example 2:
 *
 * Input: s = "a)b(c)d"
 * Output: "ab(c)d"
 *
 * Example 3:
 *
 * Input: s = "))(("
 * Output: ""
 * Explanation: An empty string is also valid.
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length <= 105
 *     s[i] is either'(' , ')', or lowercase English letter.
 */
class P1249RemoveMinParenthesis {
    String minRemoveToMakeValid(String s) {
        List<Integer> toRemoveIndex = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        int lastCharIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            switch (curr){
                case '(': {
                    stack.push(i);
                }
                break;
                case ')': {
                    if(stack.isEmpty()){
                        toRemoveIndex.add(i);
                    }else {
                        stack.pop();
//                        if(lastCharIndex > stack.peek() && lastCharIndex < i){
//                            stack.pop();
//                        }else{
//                            toRemoveIndex.add(0, stack.pop());
//                            toRemoveIndex.add(i);
//                        }
                    }
                }
                break;
                default: lastCharIndex = i;
            }
        }
        while (!stack.isEmpty()){
            toRemoveIndex.add(0, stack.pop());
        }
        return removeChars(s, toRemoveIndex);
    }

    private String removeChars(String s, List<Integer> toRemoveIndex) {
        Collections.sort(toRemoveIndex);
        StringBuilder sb = new StringBuilder();
        int fromIndex= 0;
        for (int i = 0; i < toRemoveIndex.size(); i++) {
            sb.append(s, fromIndex, toRemoveIndex.get(i));
            fromIndex = toRemoveIndex.get(i) + 1;
        }
        return sb.append(s, fromIndex, s.length()).toString();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "(()))()((((|(())()",
            "(())|(())",
            "))((|",
            "a)b(c)d|ab(c)d",
            "lee(t(c)o)de)|lee(t(c)o)de",
    })
    void test(String s, String expected){
        if(expected == null){
            expected = "";
        }
        Assert.assertEquals(expected, minRemoveToMakeValid(s));
    }
}
