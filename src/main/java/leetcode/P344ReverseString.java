package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Write a function that reverses a string. The input string is given as an array of characters s.
 *
 * You must do this by modifying the input array in-place with O(1) extra memory.
 * Input: s = ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 * Input: s = ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 * Constraints:
 *
 *     1 <= s.length <= 105
 *     s[i] is a printable ascii character.
 */
public class P344ReverseString {
    void reverseString(char[] s) {
        int beg= 0, end = s.length-1;
        while (beg<end){
            char temp = s[beg];
            s[beg] = s[end];
            s[end] = temp;
            beg++;
            end--;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "hello|olleh",
            "Hannah|hannaH",
            "a|a",
            "ab|ba",
            "abc|cba",

    })
    void test(String arg, String expected){
        char[] str = arg.toCharArray();
        reverseString(str);
        Assertions.assertArrayEquals(expected.toCharArray(), str);
    }
}
