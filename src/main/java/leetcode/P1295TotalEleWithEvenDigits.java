package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array nums of integers, return how many of them contain an even number of digits.
 *
 * Input: nums = [12,345,2,6,7896]
 * Output: 2
 * Explanation:
 * 12 contains 2 digits (even number of digits).
 * 345 contains 3 digits (odd number of digits).
 * 2 contains 1 digit (odd number of digits).
 * 6 contains 1 digit (odd number of digits).
 * 7896 contains 4 digits (even number of digits).
 * Therefore only 12 and 7896 contain an even number of digits.
 *
 * Input: nums = [555,901,482,1771]
 * Output: 1
 * Explanation:
 * Only 1771 contains an even number of digits.
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 500
 *     1 <= nums[i] <= 105
 */
class P1295TotalEleWithEvenDigits {
    int findNumbers(int[] nums) {
        int count =0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(evenDigits(num)){
                count+=1;
            }
        }
        return count;
    }

    private boolean evenDigits(int num) {
        int noOfDigits = 0;
        while(num != 0){
            num = num/10;
            noOfDigits++;
        }
        return noOfDigits%2 ==0;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"555,901,482,1771|1",
            "12,345,2,6,7896|2"})
    void test(String arrStr, int expected){
        int[] arr = Arrays.stream(arrStr.split(","))
                .mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findNumbers(arr));
    }
}
