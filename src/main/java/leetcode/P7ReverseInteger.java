package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.
 *
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 *
 *
 *
 * Example 1:
 *
 * Input: x = 123
 * Output: 321
 *
 * Example 2:
 *
 * Input: x = -123
 * Output: -321
 *
 * Example 3:
 *
 * Input: x = 120
 * Output: 21
 *
 *
 *
 * Constraints:
 *
 *     -231 <= x <= 231 - 1
 */
class P7ReverseInteger {
    int reverse(int x) {
        if(x==0||x==Integer.MAX_VALUE || x== Integer.MIN_VALUE) return 0;
        boolean isNegative = x < 0;
        x = Math.abs(x);
        List<Integer> numbers = new LinkedList<>();
        while (x != 0){
            int digit = x%10;
            x = x/10;
            numbers.add(0, digit);
        }
        for (int i = 0; i < numbers.size()/2; i++) {
            int temp = numbers.get(i);
            numbers.set(i, numbers.get(numbers.size()-1-i));
            numbers.set(numbers.size()-1-i, temp);
        }
        long result = 0;
        for (int i = 0; i < numbers.size(); i++) {
            result = (result*10)+ numbers.get(i);
        }
        if(result > Integer.MAX_VALUE){
            return 0;
        }
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        return isNegative? -(int)result:(int)result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2147483647|0",
            "-2147483648|0",
            "1534236469|0",
            "-123|-321",
            "120|21",
            "0|0",
            "10|1"
    })
    void test(int x, int expected){
        Assertions.assertEquals(expected, reverse(x));
    }
}
