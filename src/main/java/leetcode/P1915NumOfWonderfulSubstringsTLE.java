package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A wonderful string is a string where at most one letter appears an odd number of times.
 *
 *     For example, "ccjjc" and "abab" are wonderful, but "ab" is not.
 *
 * Given a string word that consists of the first ten lowercase English letters ('a' through 'j'), return the number of wonderful non-empty substrings in word. If the same substring appears multiple times in word, then count each occurrence separately.
 *
 * A substring is a contiguous sequence of characters in a string.
 *
 *
 *
 * Example 1:
 *
 * Input: word = "aba"
 * Output: 4
 * Explanation: The four wonderful substrings are underlined below:
 * - "aba" -> "a"
 * - "aba" -> "b"
 * - "aba" -> "a"
 * - "aba" -> "aba"
 *
 * Example 2:
 *
 * Input: word = "aabb"
 * Output: 9
 * Explanation: The nine wonderful substrings are underlined below:
 * - "aabb" -> "a"
 * - "aabb" -> "aa"
 * - "aabb" -> "aab"
 * - "aabb" -> "aabb"
 * - "aabb" -> "a"
 * - "aabb" -> "abb"
 * - "aabb" -> "b"
 * - "aabb" -> "bb"
 * - "aabb" -> "b"
 *
 * Example 3:
 *
 * Input: word = "he"
 * Output: 2
 * Explanation: The two wonderful substrings are underlined below:
 * - "he" -> "h"
 * - "he" -> "e"
 *
 *
 *
 * Constraints:
 *
 *     1 <= word.length <= 10^5
 *     word consists of lowercase English letters from 'a' to 'j'.
 */
class P1915NumOfWonderfulSubstringsTLE {
    class FreqAnalysis {
        Map<Character, Integer> freqMap;
        boolean allEven;
        Character oddFreqWonderfulCharacter;

        public FreqAnalysis(String subStr) {
            freqMap = new HashMap<>();
            for (int i = 0; i < subStr.length(); i++) {
                Character ch = subStr.charAt(i);
                freqMap.putIfAbsent(ch, 0);
                freqMap.put(ch, freqMap.get(ch) + 1);
            }
            resetFlags();
        }

        private void resetFlags() {
            allEven = freqMap.values().stream().allMatch(freq -> freq % 2 == 0);
            if (!allEven) {
                for (Map.Entry<Character, Integer> entry: freqMap.entrySet()){
                    if(entry.getValue() % 2 == 1){
                        if(oddFreqWonderfulCharacter == null){
                            oddFreqWonderfulCharacter = entry.getKey();
                        }else {
                            oddFreqWonderfulCharacter = null;
                            break;
                        }
                    }
                }
            }
        }

        public boolean isWonderFul(){
            return (allEven && oddFreqWonderfulCharacter == null) || (!allEven && oddFreqWonderfulCharacter != null);
        }

        public void incrementFreq(Character ch){
            freqMap.putIfAbsent(ch, 0);
            freqMap.put(ch, freqMap.get(ch)+1);
            if(ch.equals(oddFreqWonderfulCharacter)){
                oddFreqWonderfulCharacter = null;
                allEven = true;
            } else {
                if(allEven){
                    oddFreqWonderfulCharacter = ch;
                    allEven = false;
                }else{
                    resetFlags();
                }
            }
        }

        public void decrementFreq(Character ch){
            freqMap.put(ch, freqMap.get(ch)-1);
            if(freqMap.get(ch) == 0){
                freqMap.remove(ch);
            }
            if(ch.equals(oddFreqWonderfulCharacter)){
                allEven = true;
                oddFreqWonderfulCharacter = null;
            }else {
                resetFlags();
            }
        }
    }
    FreqAnalysis freqAnalysis;
    long wonderfulSubstrings(String word){
        long sol = word.length();
        for (int length = 2; length <= word.length(); length++) {
            freqAnalysis = new FreqAnalysis(word.substring(0, length));
            for (int index = 0; index < word.length()-length+1; index++) {
                if(index != 0){
                    freqAnalysis.decrementFreq(word.charAt(index-1));
                    freqAnalysis.incrementFreq(word.charAt(index+length-1));
                }
                if(freqAnalysis.isWonderFul()){
//                    System.out.println("true for" + word.substring(index, index+length));
                    sol++;
                }
            }
        }
        return sol;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "he|2|no letter appear odd noOfTimes",
            "aabb|9|Typical Case",
            "aba|4| one odd freq and one even",
            "ababba|15| two odd freq charachter"
    })
    void test(String word, long expected, String failedMsg){
        Assert.assertEquals(failedMsg, expected, wonderfulSubstrings(word));
    }
}
