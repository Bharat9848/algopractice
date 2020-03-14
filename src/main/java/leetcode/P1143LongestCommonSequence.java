package leetcode;

import misc.Solution;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
A subsequence of a string is a new string generated from the original string with some characters(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" is a subsequence of "abcde" while "aec" is not). A common subsequence of two strings is a subsequence that is common to both strings.

If there is no common subsequence, return 0.

Example 1:

Input: text1 = "abcde", text2 = "ace"
Output: 3
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:

Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:

Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.

Constraints:

    1 <= text1.length <= 1000
    1 <= text2.length <= 1000
    The input strings consist of lowercase English characters only.
Algo: at a particular point in (i, j) from String A, String B then Solution for that indexes are will be Max(Solution(i,j-1), Solution(i,j-1), Solution(i-1, j-1) + Match(i,j)?)
 */
class P1143LongestCommonSequence {
    int longestCommonSubsequence(String text1, String text2) {
        int[][] subSolution = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                int match = text1.charAt(i-1) == text2.charAt(j-1) ? 1:0;
                subSolution[i][j] = Math.max(Math.max(subSolution[i-1][j], subSolution[i][j-1]), (subSolution[i-1][j-1] + match));
            }
        }
        return subSolution[text1.length()][text2.length()];
    }


    @ParameterizedTest
    @CsvSource({
            "c,c,1",
            "c,a,0",
            "bharat,ankita,2",
            "bharat,bhagat,5"
    })
    void test(String text1, String text2, String sol){
        Assert.assertEquals(Integer.parseInt(sol), longestCommonSubsequence(text1.trim(), text2.trim()));
    }
}
