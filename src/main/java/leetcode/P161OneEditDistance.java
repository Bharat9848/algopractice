package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.
 *
 * A string s is said to be one distance apart from a string t if you can:
 *
 *     Insert exactly one character into s to get t.
 *     Delete exactly one character from s to get t.
 *     Replace exactly one character of s with a different character to get t.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "ab", t = "acb"
 * Output: true
 * Explanation: We can insert 'c' into s to get t.
 *
 * Example 2:
 *
 * Input: s = "", t = ""
 * Output: false
 * Explanation: We cannot get t from s by only one step.
 *
 *
 *
 * Constraints:
 *
 *     0 <= s.length, t.length <= 104
 *     s and t consist of lowercase letters, uppercase letters, and digits.
 */
public class P161OneEditDistance {
    public boolean isOneEditDistance(String s, String t) {
        if(s.length() - t.length() == 0){
            int diff = 0;
            for (int i = 0, j = 0; i < s.length()&& j < t.length(); i++,j++) {
                if(s.charAt(i) != t.charAt(j)){
                    diff++;
                }

                if(diff > 1){
                    return false;
                }
            }
            return diff == 1;
        } else if (Math.abs(s.length() - t.length()) == 1){
            String big = s.length() > t.length() ? s : t;
            String small = s.length() < t.length() ? s: t;
            for (int i = 0, j = 0; i < big.length() && j < small.length();) {
                if(i != j && big.charAt(i) != small.charAt(j))
                {
                    return false;
                }
                if(big.charAt(i) != small.charAt(j)){
                    i++;
                } else {
                    i++;
                    j++;
                }

            }
            return true;
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "abc|abc|false",
            "a|d|true",
            "ab|abc|true",
            "ace|ade|true",
            "sum|cum|true",
            "rent|dent|true",
            "ret|dent|false",
    })
    void test(String s, String t, boolean expected){
        Assertions.assertEquals(expected, isOneEditDistance(s,t));
    }
}
