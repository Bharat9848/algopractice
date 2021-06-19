package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
You are given a string s, return the number of segments in the string.

A segment is defined to be a contiguous sequence of non-space characters.



Example 1:

Input: s = "Hello, my name is John"
Output: 5
Explanation: The five segments are ["Hello,", "my", "name", "is", "John"]

Example 2:

Input: s = "Hello"
Output: 1

Example 3:

Input: s = "love live! mu'sic forever"
Output: 4

Example 4:

Input: s = ""
Output: 0



Constraints:

    0 <= s.length <= 300
    s consists of lower-case and upper-case English letters, digits or one of the following characters "!@#$%^&*()_+-=',.:".
    The only space character in s is ' '.


 */
public class P434CountSegmentString {
    public int countSegments(String s) {
        int count=0;
        boolean startSeg=true;
        for (char c: s.toCharArray()) {
            if(c == ' '){
                startSeg = true;
            }else{
                if(startSeg) {
                    startSeg=!startSeg;
                    count++;
                }
            }
        }
        return count;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "Hello, my name is John;5",
            "love live! mu'sic forever;4",
            "hello;1",
            "\" \";2"
    })
    void test(String input, int count){
        Assert.assertEquals(count, countSegments(input));
    }

    @Test
    public void nullStringTest(){
        Assert.assertEquals(0, countSegments(""));
        Assert.assertEquals(0, countSegments("    "));
    }
}
