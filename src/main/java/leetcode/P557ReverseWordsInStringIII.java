package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Given a string s, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
 *
 *
 * Input: s = "Let's take LeetCode contest"
 * Output: "s'teL ekat edoCteeL tsetnoc"
 * Input: s = "God Ding"
 * Output: "doG gniD"
 *
 *     1 <= s.length <= 5 * 104
 *     s contains printable ASCII characters.
 *     s does not contain any leading or trailing spaces.
 *     There is at least one word in s.
 *     All the words in s are separated by a single space
 */
public class P557ReverseWordsInStringIII {
    String reverseWords(String s) {
        int wordStart = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) ==' '){
                reverseCharsBetweenIndices(chars, wordStart, i-1);
                wordStart = i+1;
            }
        }
        reverseCharsBetweenIndices(chars, wordStart, s.length()-1);
        return new String(chars);
    }
        void reverseCharsBetweenIndices(char[] s, int beg, int end) {
            while (beg<end){
                char temp = s[beg];
                s[beg] = s[end];
                s[end] = temp;
                beg++;
                end--;
            }
        }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "Let's take LeetCode contest|s'teL ekat edoCteeL tsetnoc",
            "God Ding|doG gniD",
    })
    void test(String str, String reversed){
        Assertions.assertEquals(reversed, reverseWords(str));
    }
}
