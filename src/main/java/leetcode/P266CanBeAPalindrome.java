package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given a string s, return true if a permutation of the string could form a palindrome.
 * Input: s = "code"
 * Output: false
 * Input: s = "aab"
 * Output: true
 * Input: s = "carerac"
 * Output: true
 * Constraints:
 *
 *     1 <= s.length <= 5000
 *     s consists of only lowercase English letters.
 */
public class P266CanBeAPalindrome {
    boolean canPermutePalindrome(String s) {
        Map<Character, Integer> charToCount = IntStream.range(0, s.length()).mapToObj(i -> s.charAt(i))
                .collect(Collectors.toMap(s1 ->s1, s1 -> 1, (v1, v2) -> v1+v2));
        Integer sum = charToCount.values().stream().map(count -> count %2).reduce(0, (a, b) -> a + b);
        return  s.length()%2==1? sum == 1: sum == 0;
    }

    @ParameterizedTest
    @CsvSource({"code,false", "aab,true","carerac,true", "aaa,true", "a,true", "banaana,true", "banana,false", "anana, true"})
    void test(String s, boolean expected){
        Assertions.assertEquals(expected, canPermutePalindrome(s));
    }
}
