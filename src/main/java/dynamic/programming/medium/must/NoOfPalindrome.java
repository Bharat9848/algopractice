package dynamic.programming.medium.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string, s, return the number of palindromic substrings contained in it. A substring is a contiguous sequence of characters in a string. A palindrome is a phrase, word, or sequence that reads the same forward and backward.
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤1000≤1000
 *
 *     s consists of only lowercase English characters.
 */
public class NoOfPalindrome {
    public static int countPalindromicSubstrings(String s) {
        int count = s.length();
        boolean[][] palindromeMat = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            palindromeMat[i][i] = true;
        }
        for (int len = 2; len <= s.length(); len++) {
            for (int i = 0; i+len-1 < s.length(); i++) {
                if(s.charAt(i) == s.charAt(i+len-1)){
                    palindromeMat[i][i+len-1] = i+1 <= i+len-2 ? palindromeMat[i+1][i+len-2] : true;
                    if(palindromeMat[i][i+len-1]){
                        count++;
                    }
                } else {
                    palindromeMat[i][i+len-1] = false;
                }
            }
        }
        return count;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "a|1",
            "aa|3",
            "111|6",
            "banana|10",
            "mnm|4",
            "noon|6",
            "rotator|10"
    })
    void test(String text, int expected){
        Assertions.assertEquals(expected, countPalindromicSubstrings(text));
    }
}
