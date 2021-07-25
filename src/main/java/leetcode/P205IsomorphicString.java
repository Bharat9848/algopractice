package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.
 * Input: s = "egg", t = "add"
 * Output: true
 * Input: s = "foo", t = "bar"
 * Output: false
 * Input: s = "paper", t = "title"
 * Output: true
 *     1 <= s.length <= 5 * 104
 *     t.length == s.length
 *     s and t consist of any valid ascii character.
 */
 class P205IsomorphicString {
     boolean isIsomorphic(String s, String t) {
         Map<Character, Character> mapChars = new HashMap<>();
         Map<Character, Character> revMap = new HashMap<>();
         boolean stillIso = s.length() == t.length();
         for (int i = 0; i < s.length() && stillIso; i++) {
             Character si = s.charAt(i);
             Character di = t.charAt(i);
             mapChars.putIfAbsent(si, di);
             revMap.putIfAbsent(di, si);
             stillIso = di.equals(mapChars.get(si)) && si.equals(revMap.get(di));
         }
         return stillIso;
    }

    @ParameterizedTest
    @CsvSource( delimiter = ';', value = {
            "egg;add;true",
            "paper;title;true",
            "foo;bar;false",
            "badc;baba;false"
    })
    void test(String src, String dest, boolean expected){
        Assert.assertEquals(expected, isIsomorphic(src, dest));
    }
}
