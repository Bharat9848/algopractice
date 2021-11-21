package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "leetcode"
 * Output: 0
 *
 * Example 2:
 *
 * Input: s = "loveleetcode"
 * Output: 2
 *
 * Example 3:
 *
 * Input: s = "aabb"
 * Output: -1
 *
 *
 * use frequency map.
 * Constraints:
 *
 *     1 <= s.length <= 10^5
 *     s consists of only lowercase English letters.
 */
class P387FirstUniqueCharInString {
    int firstUniqChar(String s) {
        Map<Character, Integer> uniqueCharToIndex = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(uniqueCharToIndex.get(chars[i])==null){
                uniqueCharToIndex.put(chars[i],i);
            }else{
                uniqueCharToIndex.put(chars[i], -1);
            }
        }
        return uniqueCharToIndex.values().stream().filter(i-> i>=0).mapToInt(Integer::intValue).min().orElse(-1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aabb|-1",
            "loveleetcode|2",
            "leetcode|0",
            "aaa|-1"
    })
    void test(String s, int expected){
        Assert.assertEquals(expected, firstUniqChar(s));
    }
}
