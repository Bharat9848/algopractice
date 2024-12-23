package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Suppose you are given two strings. You need to find the length of the longest common subsequence between these two strings.
 *
 * A subsequence is a string formed by removing some characters from the original string while maintaining the relative position of the remaining characters. For example, “abd” is a subsequence of “abcd”, where the removed character is “c”.
 *
 * If there is no common subsequence, then return 0.
 *
 * Constraints:
 *
 *     1≤1≤ str1.length ≤500≤500
 *     1≤1≤ str2.length ≤500≤500
 *     str1 and str2 contain only lowercase English characters.
 */
public class LongestCommonSubsequence {
    public static int longestCommonSubsequence(String str1, String str2) {
        int[][] longLength = new int[str1.length()+1][str2.length()+1];
        for (int i = 1; i < str1.length()+1; i++) {
            for (int j = 1; j < str2.length()+1; j++) {
                longLength[i][j] = Math.max(longLength[i-1][j], Math.max(longLength[i][j-1], longLength[i-1][j-1] + (str1.charAt(i-1) == str2.charAt(j-1)? 1:0)));
            }
        }
        return longLength[str1.length()][str2.length()];
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "a|lhpyvkyrncmisozgoostrdtvpvhsbwmspxtxkgfftxaetfxkhldwdscbzaxxltoywswiuarisaecttbbujnroffluwtcuucrbmvmeozofpjveaknfulycqbplmtkhqchjnvvkrpuxooxzvfuvgwseowtdzfrhlicgdnrcxxfxnyrvkekdkszffwpefmedbjybtupzzraddnmxyemvqdimnnltgcgfqimcvwhp|1",
            "found|cloud|3",
            "bed|read|2",
            "bed|hall|0",
            "educative|education|7",
            "redeem|freedom|4",
            "freedom|redeem|4",
    })
    void test(String str1, String str2, int expected){
        Assertions.assertEquals(expected, longestCommonSubsequence(str1, str2));
    }
}
