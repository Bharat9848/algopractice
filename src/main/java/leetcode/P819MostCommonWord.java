package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given a string paragraph and a string array of the banned words banned, return the most frequent word that is not banned. It is guaranteed there is at least one word that is not banned, and that the answer is unique.
 *
 * The words in paragraph are case-insensitive and the answer should be returned in lowercase.

 * Example 1:
 *
 * Input: paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]
 * Output: "ball"
 * Explanation:
 * "hit" occurs 3 times, but it is a banned word.
 * "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
 * Note that words in the paragraph are not case sensitive,
 * that punctuation is ignored (even if adjacent to words, such as "ball,"),
 * and that "hit" isn't the answer even though it occurs more because it is banned.
 *
 * Example 2:
 *
 * Input: paragraph = "a.", banned = []
 * Output: "a"
 *
 *
 *
 * Constraints:
 *
 *     1 <= paragraph.length <= 1000
 *     paragraph consists of English letters, space ' ', or one of the symbols: "!?',;.".
 *     0 <= banned.length <= 100
 *     1 <= banned[i].length <= 10
 *     banned[i] consists of only lowercase English letters.
 */
public class P819MostCommonWord {
    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> bannedSet = new HashSet<>();
        for (int i = 0; i < banned.length; i++) {
            bannedSet.add(banned[i].toLowerCase());
        }
        Map<String, Integer> freqMap = new HashMap<>();
        String[] paragraphWords = paragraph.split("[!?',;. \"]+");
         for(String word: paragraphWords){
             word = word.toLowerCase();
             if(bannedSet.contains(word)){
                 continue;
             }else{
                 freqMap.putIfAbsent(word, 0);
                 freqMap.put(word, freqMap.get(word) +1);
             }
        }
        String maxWord = freqMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).orElseThrow();
        return maxWord;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"a.||a", "a. ban nml,nml,nml a|ban|nml", "Bob hit a ball, the hit BALL flew far after it was hit.|hit|ball"})
    void test(String paragraph, String bannedStr, String expected){
        if(bannedStr==null){bannedStr="";}
        Assert.assertEquals(expected,
                mostCommonWord(paragraph, bannedStr.split(",")));
    }
}
