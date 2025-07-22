package hash.map.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Suppose you are given an array of strings, words, and each element in the array has a length of two. You need to return the length of the longest palindrome that can be made by concatenating some elements from words. If no palindrome can be made, return 0.
 *
 * A palindrome is a sequence of characters that reads the same forward and backward.
 *
 * Constraints:
 *
 *     1≤1≤ words.length ≤1000≤1000
 *     words[i].length =2=2
 *     Each word can be used at most once.
 *     words should only consist of lowercase English letters
 */
public class LongestPalidromeConcatTwoWordString {

    public int longestPalindrome(String[] words) {
        int count = 0;
        Map<String, Integer> stringCount = new HashMap<>();
        for(String word: words){
            String reverseWord = "" + word.charAt(1) + word.charAt(0);
            if(stringCount.containsKey(reverseWord)){
                count += 4;
                var prevCount = stringCount.remove(reverseWord);
                if(prevCount-1 > 0){
                    stringCount.put(reverseWord, prevCount -1);
                }
            } else {
                stringCount.put(word, stringCount.getOrDefault(word, 0) + 1);
            }
        }
        for (var str : stringCount.keySet()){
            if(str.charAt(0) == str.charAt(1)){
                count += 2;
                break;
            }
        }
        return count;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "aa,aa|4",
            "ab,cd,ba|4",
            "ab,ab,ab,xy,ab,ba,ba,yx|12",
            "aa,xx,cd,xx,cc,xx,cc,aa|14",
            "aa,cc,xx,cc,xx,cc,xx,aa|14",
            "ab,cd,ef,gh,ij|0",
            "aa,bb,cc|2",
            "aa,bb,aa,ea|6"

    })
    void test(String wordsStr, int expected){
        Assertions.assertEquals(expected, longestPalindrome(wordsStr.split(",")));
    }
}
