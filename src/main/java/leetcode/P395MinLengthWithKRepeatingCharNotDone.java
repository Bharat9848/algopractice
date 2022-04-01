package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.
 *
 * Input: s = "aaabb", k = 3
 * Output: 3
 * Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.
 * Input: s = "ababbc", k = 2
 * Output: 5
 * Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 *
 *use Divide and conquer
 *
 * Constraints:
 *
 *     1 <= s.length <= 104
 *     s consists of only lowercase English letters.
 *     1 <= k <= 105
 */
class P395MinLengthWithKRepeatingCharNotDone {
    int longestSubstring(String s, int k) {
        return 0;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aaabb|3|3",
            "ababbc|2|5",
    })
    void test(String s, int k, int expected){
        Assertions.assertEquals(expected, longestSubstring(s, k));
    }
}
