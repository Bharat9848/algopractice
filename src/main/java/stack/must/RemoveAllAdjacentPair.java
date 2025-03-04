package stack.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Stack;

/**
 * You are given a string consisting of lowercase English letters. Repeatedly remove adjacent duplicate letters, one pair at a time. Both members of a pair of adjacent duplicate letters need to be removed.
 *
 * Constraints:
 *
 *     1≤1≤ string.length ≤103≤103
 *     string consists of lowercase English alphabets.
 */
public class RemoveAllAdjacentPair {

    public static String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        stack.push(chars[0]);
        for (int i = 1; i < s.length(); i++) {
            if(!stack.empty() && stack.peek() == chars[i]){
                stack.pop();
            } else {
                stack.push(chars[i]);
            }
        }
        char[] resultArr = new char[stack.size()];
        for (int i = resultArr.length-1; i >= 0; i--) {
            resultArr[i] = stack.pop();
        }
        return new String(resultArr);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aa|",
            "aaa|a",
            "a|a",
            "baab|",
            "baaab|bab"
    })
    public void test(String s, String expected){
        if(expected == null){
            expected = "";
        }
        Assertions.assertEquals(expected, removeDuplicates(s));
    }
}
