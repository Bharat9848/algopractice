package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string s and an integer k, return the total number of
 * substrings
 * of s where at least one character appears at least k times.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abacb", k = 2
 *
 * Output: 4
 *
 * Explanation:
 *
 * The valid substrings are:
 *
 *     "aba" (character 'a' appears 2 times).
 *     "abac" (character 'a' appears 2 times).
 *     "abacb" (character 'a' appears 2 times).
 *     "bacb" (character 'b' appears 2 times).
 *
 * Example 2:
 *
 * Input: s = "abcde", k = 1
 *
 * Output: 15
 *
 * Explanation:
 *
 * All substrings are valid because every character appears at least once.
 *
 *
 *
 * Constraints:
 *
 *     1 <= s.length <= 3000
 *     1 <= k <= s.length
 *     s consists only of lowercase English letters.
 */
public class P3325CountFreqWithKCharacterINotDone {
    public int numberOfSubstringsNotDone(String s, int k) {
        int left = 0, right=0;
        int result = 0;
        int currentCount = 0;
        Character currentChar = null;
        Map<Character, Integer> freqMap = new HashMap<>();
        //init window
        int i = 0;
        for ( ;i < s.length(); i++) {
            char ch = s.charAt(i);
            freqMap.putIfAbsent(ch,  0);
            int newCount = freqMap.get(ch) + 1;
            freqMap.put(ch, newCount);
            if(newCount == k){
               currentCount = k;
               currentChar = ch;
                break;
            }
        }


        if(i < s.length()){
            result++;
            for (int j = i+1; j < s.length(); j++) {
                char ch = s.charAt(j);
                if(ch == currentChar){
                    currentCount++;
                }
                freqMap.put(ch, freqMap.getOrDefault(ch, 0) +1);
                result++;
            }
            while(left < s.length()){
                char leftChar = s.charAt(left);
                if(leftChar == currentChar){
                    if(currentCount > k){
                        freqMap.put(currentChar, currentCount - 1);
                        result++;
                        currentCount--;
                    } else {
                        freqMap.put(currentChar, currentCount -1);
                        currentCount--;
                        for (Character ch: freqMap.keySet()) {
                            if(freqMap.get(ch) >= k){
                                currentCount = freqMap.get(ch);
                                currentChar = ch;
                                result++;
                                break;
                            }
                        }
                        if(currentCount < k){
                            break;
                        }
                    }

                } else{
                    freqMap.put(leftChar, freqMap.get(leftChar) -1);
                    result++;
                }
                    left++;
            }
        } else {
            if(currentCount == k ){
                return 1;
            }
            else {
                throw new RuntimeException("wrong input = k > s.length()");
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "abacb|2|4",
            "abcde|1|15",

    })
    void test(String s, int k, int expected){
        Assertions.assertEquals(expected, numberOfSubstringsNotDone(s, k));
    }
}
