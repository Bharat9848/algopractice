package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Give a binary string s, return the number of non-empty substrings that have the same number of 0's and 1's, and all the 0's and all the 1's in these substrings are grouped consecutively.
 *
 * Substrings that occur multiple times are counted the number of times they occur.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "00110011"
 * Output: 6
 * Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", "1100", "10", "0011", and "01".
 * Notice that some of these substrings repeat and are counted the number of times they occur.
 * Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.
 *
 * Example 2:
 *
 * Input: s = "10101"
 * Output: 4
 * Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
 *
 * TODO O(n) - solution Algorithm
 *
 * Let's create groups as defined above. The first element of s belongs in it's own group. From then on, each element either doesn't match the previous element, so that it starts a new group of size 1; or it does match, so that the size of the most recent group increases by 1.
 *
 * Afterwards, we will take the sum of min(groups[i-1], groups[i]).
 *
 * Constraints:
 *
 *     1 <= s.length <= 105
 *     s[i] is either '0' or '1'.
 */
class P696CountBinaryStringNotOptimized {
    int countBinarySubstringsTimeLimitExceed(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            int currentCharLength = 1;
            for (int j = i + 1; j < s.length() && s.charAt(j) == currentChar; j++) {
                currentCharLength++;
            }
            for (int j = i + currentCharLength; j < s.length() && s.charAt(j) != currentChar && currentCharLength > 0; j++) {
                currentCharLength--;
            }
            if (currentCharLength == 0) {
                count++;
            }
        }
        return count;
    }

    int countBinarySubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length()-1; ) {
            if(s.charAt(i) != s.charAt(i+1)){
                int leftIndex = i, rightIndex = i+1;
                char leftChar = s.charAt(leftIndex);
                char rightChar = s.charAt(rightIndex);
                while(leftIndex >= 0 && rightIndex < s.length() && s.charAt(leftIndex) == leftChar && s.charAt(rightIndex) == rightChar){
                    count++;
                    leftIndex--;
                    rightIndex++;
                }
                if(rightIndex == i+1){ i= rightIndex;} else {i = rightIndex-1;}
            }else {
                i++;
            }
        }
        return count;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0000|0",
            "01|1",
            "101|2",
            "1010|3",
            "10101|4",
            "00110011|6"
    })
    void test(String s, int expected){
        Assert.assertEquals(expected, countBinarySubstrings(s));
    }
}
