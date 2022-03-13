package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 * In other words, return true if one of s1's permutations is the substring of s2.
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 *     1 <= s1.length, s2.length <= 104
 *     s1 and s2 consist of lowercase English letters.
 */
public class P567PermutationInString {
    boolean checkInclusion1(String s1, String s2) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            Character ch = s1.charAt(i);
            freqMap.putIfAbsent(ch, 0);
            freqMap.put(ch, freqMap.get(ch) + 1);
        }
        if (s1.length() > s2.length()) {
            return false;
        } else {
            for (int i = 0; i < s2.length() - s1.length() + 1; i++) {
                Map<Character, Integer> currentMap = new HashMap<>(freqMap);
                for (int j = i; j < i + s1.length(); j++) {
                    char charToCheck = s2.charAt(j);
                    if (currentMap.containsKey(charToCheck)) {
                        int freq = currentMap.get(charToCheck) - 1;
                        if (freq == 0) {
                            currentMap.remove(charToCheck);
                        } else {
                            currentMap.put(charToCheck, freq);
                        }
                    } else {
                        break;
                    }
                }
                if (currentMap.isEmpty()) {
                    return true;
                }
            }
            return false;
        }
    }


        boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length())
                return false;
            int[] s1map = new int[26];
            int[] s2map = new int[26];
            for (int i = 0; i < s1.length(); i++) {
                s1map[s1.charAt(i) - 'a']++;
                s2map[s2.charAt(i) - 'a']++;
            }
            for (int i = 0; i < s2.length() - s1.length(); i++) {
                if (matches(s1map, s2map))
                    return true;
                s2map[s2.charAt(i + s1.length()) - 'a']++;
                s2map[s2.charAt(i) - 'a']--;
            }
            return matches(s1map, s2map);
        }

        public boolean matches(int[] s1map, int[] s2map){
            for (int i = 0; i < 26; i++) {
                if (s1map[i] != s2map[i])
                    return false;
            }
            return true;
        }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value ={
            "ab|eidbaooo|true",
            "ab|eidboaoo|false",
    })
    void test(String s1, String s2, boolean expected){
        Assertions.assertEquals(expected, checkInclusion(s1,s2));
    }
}
