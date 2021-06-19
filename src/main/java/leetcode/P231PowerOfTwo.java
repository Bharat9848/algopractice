package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
Given an integer n, return true if it is a power of two. Otherwise, return false.

An integer n is a power of two, if there exists an integer x such that n == 2x.



Example 1:

Input: n = 1
Output: true
Explanation: 20 = 1

Example 2:

Input: n = 16
Output: true
Explanation: 24 = 16

Example 3:

Input: n = 3
Output: false

Example 4:

Input: n = 4
Output: true

Example 5:

Input: n = 5
Output: false



Constraints:

    -231 <= n <= 231 - 1

 */
public class P231PowerOfTwo {
    public boolean isPowerOfTwo(int n) {
        return n>0&&(n&n-1)==0;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2;true", //0, 1, 10, 11, 100 = 0*2^0 + 0*2^1 + 1*2^2
            "3;false",
            "32;true",
            "-512;false",
            "90;false",
            "100;false",
            "101;false",
            "99;false",
            "-1;false",
            "0;false",
            "1;true"

    })
    void test(Integer no, Boolean expected){
        Assert.assertEquals(expected, isPowerOfTwo(no));
    }
}
