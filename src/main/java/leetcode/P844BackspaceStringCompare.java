package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

/**
 * Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means a backspace character.
 *
 * Note that after backspacing an empty text, the text will continue empty.
 * Input: s = "ab#c", t = "ad#c"
 * Output: true
 * Input: s = "ab##", t = "c#d#"
 * Output: true
 * Input: s = "a#c", t = "b"
 * Output: false
 *
 *  Constraints:
 *
 *     1 <= s.length, t.length <= 200
 *     s and t only contain lowercase letters and '#' characters.
 *
 *
 *
 * Follow up: Can you solve it in O(n) time and O(1) space?
 */
public class P844BackspaceStringCompare {

    boolean backspaceCompare(String s, String t) {
        boolean matched = true;
        Pair<Integer, Integer> currentS = getNextChar(s, new Pair<>(s.length(), 0));
        Pair<Integer, Integer> currentT = getNextChar(t, new Pair<>(t.length(), 0));
        while(currentS.getFirst() >= 0  && currentT.getFirst() >= 0 && matched){
            matched = s.charAt(currentS.getFirst()) == t.charAt(currentT.getFirst());
            currentS = getNextChar(s, currentS);
            currentT = getNextChar(t, currentT);
        }
        return matched && (currentS.getFirst() == currentT.getFirst());
    }

    private Pair<Integer, Integer> getNextChar(String str, Pair<Integer, Integer> lastCharToTrailingBackSpaces) {
        int lastIndex = lastCharToTrailingBackSpaces.getFirst();
        int trailingBackSpaces = lastCharToTrailingBackSpaces.getSec();
        int current = lastIndex-1;
        while (current >=0 ){
            if(str.charAt(current)== '#'){
                trailingBackSpaces++;
            }else if (trailingBackSpaces > 0){
                trailingBackSpaces--;
            }else {
               return new Pair<>(current, trailingBackSpaces);
            }
            current--;
        }
        return new Pair<>(-1,0);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "bxj##tw|bxj###tw|false",
            "ab#c|ad#c|true",
            "ab##|c#d#|true",
            "a#c|b|false"
    })
    void test(String a, String b, boolean expected){
        Assertions.assertEquals(expected, backspaceCompare(a, b));
    }
}
