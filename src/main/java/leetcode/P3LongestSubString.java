package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a string s, find the length of the longest substring without repeating characters.
 * Input: s = "abcabcbb"
 * Output: 3
 * Input: s = "bbbbb"
 * Output: 1
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Constraints:
 *
 *     0 <= s.length <= 5 * 104
 *     s consists of English letters, digits, symbols and spaces.
 */
public class P3LongestSubString {
    int lengthOfLongestSubstring(String s) {
        if(s == null|| s.isEmpty()){
            return 0;
        }
        Map<Character, Integer> charToPosition = new HashMap<>();
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if(charToPosition.containsKey(curr)){
                break;
            }else {
                maxLength++;
                charToPosition.put(curr, i);
            }
        }
        int left = 0;
        int currentLen = maxLength;
        for (int i = maxLength; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (charToPosition.containsKey(ch)) {
                int lastIndexChSeen = charToPosition.get(ch);
                if(lastIndexChSeen >= left){
                    currentLen = (i - lastIndexChSeen -1);
                    left = lastIndexChSeen+1;
                }
            }
            currentLen++;
            charToPosition.put(ch, i);
            maxLength = Math.max(currentLen, maxLength);
        }
        return maxLength;
    }
    @ParameterizedTest
    @CsvSource({
            "bpfbhmipx,7",
            "anviaj,5",
            "abcabcbb,3",
            "bbbbb,1",
            "pwwkew,3",
            "pwkeaw,5",
            "pwpa,3",
    })
    void test(String str, int expected){
        Assertions.assertEquals(expected, lengthOfLongestSubstring(str));
    }
}
