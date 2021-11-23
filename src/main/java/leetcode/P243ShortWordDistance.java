package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.
 *
 *
 *
 * Example 1:
 *
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
 * Output: 3
 *
 * Example 2:
 *
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     1 <= wordsDict.length <= 3 * 104
 *     1 <= wordsDict[i].length <= 10
 *     wordsDict[i] consists of lowercase English letters.
 *     word1 and word2 are in wordsDict.
 *     word1 != word2
 */
public class P243ShortWordDistance {
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int distance = Integer.MAX_VALUE;
        int word1Index = -1, word2Index = -1;
        for (int i = 0; i < wordsDict.length; i++) {
            String word = wordsDict[i];
            if(word.equals(word1)){
                word1Index = i;
            }
            if(word.equals(word2)){
                word2Index = i;
            }
            if(word1Index != -1 && word2Index != -1 && distance > Math.abs(word2Index-word1Index)){
                distance = Math.abs(word2Index-word1Index);
            }
        }
        return distance;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "practice,makes,perfect,coding,makes|makes|coding|1",
            "practice,makes,perfect,coding,makes|coding|practice|3"
    })
    void test(String dictStr, String word1, String word2, int expected){
        Assert.assertEquals(expected, shortestDistance(dictStr.split(","), word1, word2));
    }
}
