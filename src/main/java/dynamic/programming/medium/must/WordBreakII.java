package dynamic.programming.medium.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * You are given a string, s, and an array of strings, wordDict, representing a dictionary. Your task is to add spaces to s to break it up into a sequence of valid words from wordDict. We are required to return an array of all possible sequences of words (sentences). The order in which the sentences are listed is not significant.
 *
 *     Note: The same dictionary word may be reused multiple times in the segmentation.
 *
 * Constraints:
 *
 *     1≤1≤ s.length ≤20≤20
 *
 *     1≤1≤ wordDict.length ≤1000≤1000
 *
 *     1≤1≤ wordDict[i].length ≤10≤10
 *
 *     s and wordDict[i] consist of only lowercase English letters.
 *
 *     All the strings of wordDict are unique.
 */
public class WordBreakII {
    public static List<String> wordBreak(String s, List<String> WordDict) {
        return new ArrayList<>();
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "catsanddog|cat,cats,sand,and,dog|cat sand dog,cats and dog"
    })
    void test(String s, String wordStr, String expectedList){
        var wordList = Arrays.stream(wordStr.split(",")).toList();
        var result = wordBreak(s, wordList);
        Assertions.assertEquals(Arrays.stream(expectedList.split(",")).toList(), result);
    }
}
