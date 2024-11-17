package two.pointers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Given a sentence, reverse the order of its words without affecting the order of letters within the given word.
 *
 * Constraints:
 *
 *     The sentence contains English uppercase and lowercase letters, digits, and spaces.
 *
 *     1≤1≤ sentence.length ≤104≤104
 *
 *     The order of the letters within a word is not to be reversed.
 *
 *     Note: The input string may contain leading or trailing spaces or multiple spaces between words. The returned string, however, should only have a single space separating each word. Do not include any extra spaces.
 */
public class ReverseWordInSentence {
    public static String reverseWords(String sentence) {
        char[] chars = sentence.toCharArray();
        Map.Entry<Integer, Integer> trimmedStartEnd = trimSpace(chars);
        int startSpace = -1;
        int wordStart = -1, wordEnd = -1;
        for (int i = trimmedStartEnd.getKey(); i <= trimmedStartEnd.getValue(); i++) {
            if (chars[i] == ' ') {
                if (wordStart != -1) {
                    reverseWord(chars, wordStart, wordEnd);
                    wordStart = -1;
                    startSpace = i;
                } else {
                    if (startSpace == -1) {
                        startSpace = i;
                    }
                }
            } else {
                if (wordStart == -1) {
                    wordStart = i;
                    wordEnd = i;
                } else {
                    wordEnd++;
                }
            }
        }
        if (wordStart != -1) {
            reverseWord(chars, wordStart, wordEnd);
        }
        reverseWord(chars, trimmedStartEnd.getKey(), trimmedStartEnd.getValue());
        return new String(chars, trimmedStartEnd.getKey(), trimmedStartEnd.getValue() - trimmedStartEnd.getKey() + 1);
    }

    private static Map.Entry<Integer, Integer> trimSpace(char[] chars) {
        int start = -1, end =-1;
        int sndSpace = -1;
        int i = 0;
        //ignore leading spaces
        for ( ; i < chars.length && chars[i] == ' '; i++) {}
        //find first word
        start = i;
        for (; i < chars.length && chars[i] != ' '; i++) {}
        //set to extra space
        int extraSpace = i;
        //find 2nd word first character
        for ( ; i < chars.length && chars[i] == ' '; i++) {}
        int nonSpaceChar = i;
        while(nonSpaceChar < chars.length) {
            if(chars[nonSpaceChar] != ' '){
                extraSpace = extraSpace + 1;
                while(nonSpaceChar < chars.length && chars[nonSpaceChar] != ' ') {
                    char temp = chars[extraSpace];
                    chars[extraSpace] = chars[nonSpaceChar];
                    chars[nonSpaceChar] = temp;
                    extraSpace++;
                    nonSpaceChar++;
                }
            } else {
                nonSpaceChar++;
            }
        }
        end = extraSpace - 1;
        return new AbstractMap.SimpleEntry(start, end);
    }

    private static void reverseWord(char[] chars, int wordStart, int wordEnd) {
        int left=wordStart, right = wordEnd;
        while(left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "hello world|world hello",
            "hello   world|world   hello",
    })
    void test(String arg, String expected){
        Assertions.assertEquals(expected,reverseWords(arg));
    }

    @Test
    void test1(){

        String[] str = new String[] {"A I am a adam     ", "   Hello world    rust    ", "    hello   ", "   a  bd    cef   "};
        String[] expected = new String[] {"adam a am I A", "rust world Hello", "hello", "cef bd a"};
        for (int i = 0; i < str.length; i++) {
            Assertions.assertEquals(expected[i], reverseWords(str[i]));
        }

    }
}
