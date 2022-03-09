package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a 0-indexed string word and a character ch, reverse the segment of word that starts at index 0 and ends at the index of the first occurrence of ch (inclusive). If the character ch does not exist in word, do nothing.
 *
 *     For example, if word = "abcdefd" and ch = "d", then you should reverse the segment that starts at 0 and ends at 3 (inclusive). The resulting string will be "dcbaefd".
 *
 * Return the resulting string.
 *
 *
 *
 * Example 1:
 *
 * Input: word = "abcdefd", ch = "d"
 * Output: "dcbaefd"
 * Explanation: The first occurrence of "d" is at index 3.
 * Reverse the part of word from 0 to 3 (inclusive), the resulting string is "dcbaefd".
 *
 * Example 2:
 *
 * Input: word = "xyxzxe", ch = "z"
 * Output: "zxyxxe"
 * Input: word = "abcd", ch = "z"
 * Output: "abcd"
 * Explanation: "z" does not exist in word.
 * You should not do any reverse operation, the resulting string is "abcd".
 *
 *
 *
 * Constraints:
 *
 *     1 <= word.length <= 250
 *     word consists of lowercase English letters.
 *     ch is a lowercase English letter.
 */
class P2000ReversePrefixString {
    String reversePrefix(String word, char ch) {
        char[] wordChars = word.toCharArray();
        int i = 0;
        for (; i < wordChars.length; i++) {
            if(wordChars[i] == ch){
                break;
            }
        }
        if(i == wordChars.length){
            return word;
        }else {
            int beg =0, end =i;
            while (beg < end){
                char temp = wordChars[beg];
                wordChars[beg] = wordChars[end];
                wordChars[end] = temp;
                beg++;
                end--;
            }
        }
        return new String(wordChars);
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "xyxzxe|z|zxyxxe",
            "abcd|z|abcd",
            "a|a|a",
            "ag|g|ga",
    })
    void test(String word, char ch, String expected){
        Assertions.assertEquals(expected, reversePrefix(word,ch));
    }
}
