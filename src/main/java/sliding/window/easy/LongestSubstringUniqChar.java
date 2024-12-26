package sliding.window.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, str, return the length of the longest substring without repeating characters.
 *
 * Constraints:
 *
 *     11 ≤≤ str.length ≤105≤105
 *     str consists of English letters, digits, and spaces.
 */

/**
 * We will maintain a map of whats in the window. we increase the window if new char is not present in our window. If char is already present in the window then stop the window expansion and check if it is the maximum length window.
 * todo we can do this if without removing chars from the window.
 */
public class LongestSubstringUniqChar {
    public static int findLongestSubstring(String str) {
        int left = 0;
        int maxLength = 0;
        char[] schars = str.toCharArray();
        Set<Character> windowchars = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            char current = schars[i];
            if(windowchars.contains(current)){
                maxLength = Math.max(maxLength, i - 1 - left + 1);
                for (int j = left; j < i; j++) {
                    if(schars[j] == current){
                        left = j+1;
                        break;
                    } else {
                        windowchars.remove(schars[j]);
                    }
                }
            } else {
                windowchars.add(current);
            }
        }
        return Math.max(maxLength, windowchars.size());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "bbbbb|1",
            "abcabcd|4",
            "conceptoftheday|8",
            "racecar|4",
            "bankrupted|10"
    })
    void test(String str, int expected){
        Assertions.assertEquals(expected, findLongestSubstring(str));
    }
}
