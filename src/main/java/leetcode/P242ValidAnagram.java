package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length, t.length <= 5 * 104
 *     s and t consist of lowercase English letters.
 *
 * Follow up: What if the inputs contain Unicode characters? How would you adapt your solution to such a case?
 * O(n) -> using hashmap store all the character count from s and from t for each char decrease the count and delete the key if its count reaches zero. In the end map should be empty
 */
public class P242ValidAnagram {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "anagram|nagaram|true",
            "rat|car|false"
    })
    void testHappyCase(String s, String t, boolean expected){
        Assert.assertEquals(expected, isAnagram(s, t));
    }

    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> countMap =  new HashMap<>();
        char[] chars = s.toCharArray();
        for (char nextChar : chars) {
            countMap.putIfAbsent(Character.valueOf(nextChar), 0);
            countMap.put(Character.valueOf(nextChar), countMap.get(nextChar) + 1);
        }
        for (char matchChar: t.toCharArray()) {
            Integer charCount = countMap.get(matchChar);
            if(charCount == null){
                return false;
            } else {
                if(charCount==1){
                    countMap.remove(matchChar);
                }else{
                    countMap.put(matchChar, countMap.get(matchChar) - 1);
                }
            }
        }
        return countMap.isEmpty();
    }
}
