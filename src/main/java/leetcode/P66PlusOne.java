package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading 0's.
 *
 * Increment the large integer by one and return the resulting array of digits.
 *
 * Input: digits = [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 * Incrementing by one gives 123 + 1 = 124.
 * Thus, the result should be [1,2,4].
 *
 * Input: digits = [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 * Incrementing by one gives 4321 + 1 = 4322.
 * Thus, the result should be [4,3,2,2].
 *
 *  Input: digits = [9]
 * Output: [1,0]
 * Explanation: The array represents the integer 9.
 * Incrementing by one gives 9 + 1 = 10.
 * Thus, the result should be [1,0].
 */
public class P66PlusOne {
    public int[] plusOne(int[] digits) {
        boolean carry = false;
        for (int i = digits.length-1; i >=0 ; i--) {
            int dig = digits[i];
            if(dig == 9){
                digits[i] = 0;
                carry = true;
            }else{
                digits[i] = digits[i] + 1;
                carry = false;
                break;
            }
        }
        if(carry){
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            return result;
        }else{
            return digits;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value =  {"1,2,3|1,2,4",
        "2,3,3,9,9|2,3,4,0,0",
            "9,9,9|1,0,0,0"
    })
    void test(String digitStr, String expectedStr){
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] digits = Arrays.stream(digitStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expected, plusOne(digits));
    }
}
