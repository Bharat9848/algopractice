package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a string s and an array of strings words, return the number of words[i] that is a subsequence of s.
 *
 * A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
 *
 *     For example, "ace" is a subsequence of "abcde".
 *
 * Input: s = "abcde", words = ["a","bb","acd","ace"]
 * Output: 3
 * Explanation: There are three strings in words that are a subsequence of s: "a", "acd", "ace".
 *
 * Example 2:
 *
 * Input: s = "dsahjpjauf", words = ["ahjpjau","ja","ahbwzgqnuk","tnmlanowax"]
 * Output: 2
 * Constraints:
 *
 *     1 <= s.length <= 5 * 104
 *     1 <= words.length <= 5000
 *     1 <= words[i].length <= 50
 *     s and words[i] consist of only lowercase English letters.
 */
public class P792NoOfMatchingSubSeqTLE {
    int numMatchingSubseq(String s, String[] words) {
        Map<String, Integer> wordToCount = Arrays.stream(words).collect(Collectors.toMap(str->str, str -> 1, (v1, v2) -> v1+v2));
        Set<String> lastIndexSubSeq = new HashSet<>();
        lastIndexSubSeq.add("");
        int count = wordToCount.getOrDefault("",0);
        for (int i = 0; i < s.length(); i++) {
            Set<String> newIndexSubSeq = new HashSet<>();
            for (String subsq: lastIndexSubSeq){
                newIndexSubSeq.add(subsq);
                String newSubSeq = subsq + s.charAt(i);
                newIndexSubSeq.add(newSubSeq);
                count += wordToCount.getOrDefault(newSubSeq,0);
            }
            lastIndexSubSeq = newIndexSubSeq;
        }
        return count;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "dsahjpjauf|ahjpjau,ja,ahbwzgqnuk,tnmlanowax|2",
            "abcde|a,bb,acd,ace|3",
            "abcde||1",
            "a|a|1",
            "qlhxagxdqh|qlhxagxdq,qlhxagxdq,lhyiftwtut,yfzwraahab|2"
            })
    void test(String s, String wordsStr, int expected){
        if(wordsStr == null){
            wordsStr = "";
        }
        Assertions.assertEquals(expected, numMatchingSubseq(s, wordsStr.split(",")));
    }
}
