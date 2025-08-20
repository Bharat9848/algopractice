package array.medium.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * TODO
 * Given a string num_str representing a palindrome, the string only contains digits. Your task is to return the next possible palindrome using the same digits. The returned palindrome would be the next largest palindrome, meaning there can be more than one way to rearrange the digits to make a larger palindrome. Return an empty string if no greater palindrome can be made.
 *
 * Consider the following example to understand the expected output for a given numeric string:
 *
 *     input string = "123321"
 *
 *     possible palindromes = "213312", "231132", "312213", "132231", "321123"
 *
 *     We should return the palindrome "132231" as it is greater than "123321" yet the smallest palindrome excluding the original palindrome.
 *
 * Constraints:
 *
 *     1≤1≤ num.length ≤105≤105
 *
 *     num_str is a palindrome.
 */

public class NextPalindromeInSequence {
    public static String findNextPalindromeNotDone(String numStr) {
        return "";
    }

    private static void swapChars(char[] chars, int s, int t){
        char temp = chars[s];
        chars[s] = chars[t];
        chars[t] = temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "11111|",
            "908695596809|908695596809",
            "987789|",
            "5|",
            "767|",
    })
    void test(String numStr, String expected){
        if(expected == null){
            expected = "";
        }
        Assertions.assertEquals(expected, findNextPalindromeNotDone(numStr));
    }
}
