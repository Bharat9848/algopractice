package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer array nums, move all the even integers at the beginning of the array followed by all the odd integers.
 *
 * Return any array that satisfies this condition.
 * Example 1:
 *
 * Input: nums = [3,1,2,4]
 * Output: [2,4,3,1]
 * Explanation: The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
 *
 * Example 2:
 *
 * Input: nums = [0]
 * Output: [0]
 * Constraints:
 *
 *     1 <= nums.length <= 5000
 *     0 <= nums[i] <= 5000
 */
class P905SortByParity {

    int[] sortArrayByParity(int[] nums) {
        int nextEvenLoc = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]%2==0){
                int temp = nums[i];
                nums[i] = nums[nextEvenLoc];
                nums[nextEvenLoc] = temp;
                nextEvenLoc++;
            }
        }
        return nums;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4|2,4,3,1",
            "4,2,6,8|4,2,6,8",
            "1,3,5|1,3,5",
            "0|0"
    })
    void test(String numsStr, String expectedStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertArrayEquals(expected, sortArrayByParity(nums));
    }
}
