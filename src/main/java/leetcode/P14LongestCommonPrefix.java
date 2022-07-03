package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * nstraints:
 *
 *     1 <= strs.length <= 200
 *     0 <= strs[i].length <= 200
 *     strs[i] consists of only lowercase English letters.
 */
public class P14LongestCommonPrefix {
    String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }
        if(strs.length == 1) {
            return strs[0];
        }
        StringBuilder sb = new StringBuilder("");
        boolean done = false;
        for (int i = 0; i < strs[0].length() && !done; i++) {
            char ch = strs[0].charAt(i);
            if(allHaveSameChar(ch, i, strs)){
                sb.append(ch);
            }else{
                done = true;
            }
        }
        return sb.toString();
    }

    private boolean allHaveSameChar(char ch, int i, String[] strs) {
        boolean allSame = true;
        for (int j = 1; j < strs.length && allSame; j++) {
            allSame = i < strs[j].length() && strs[j].charAt(i) == ch;
        }
        return allSame;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value={
            "flower,flow,flight|fl",
            "dog,racecar,car|",
            "ab,a|a",
            "aaa,aa,aaa|aa"
    })
    void test(String arrStr, String expected){
        expected = expected == null ? "": expected;
        Assertions.assertEquals(expected, longestCommonPrefix(arrStr.split(",")));
    }
}
