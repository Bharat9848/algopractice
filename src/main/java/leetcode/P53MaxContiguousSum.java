package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [1]
 * Output: 1
 * <p>
 * Example 3:
 * <p>
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 3 * 104
 * -105 <= nums[i] <= 105
 *
 * <p>
 * at a particular i sum can be either sum(Nums[3...6]) & sumContigAt(0..6 ) & nums[6] & sum(0,1)
 */
class P53MaxContiguousSum {
    int maxSubArray(int[] nums) {
        int result = nums[0];
        int total = nums[0], csumTillCurr = nums[0];
        for (int i = 1; i < nums.length; i++) {
            total = total + nums[i];
            csumTillCurr = Math.max(csumTillCurr + nums[i], nums[i]);
            result = Math.max(result, Math.max(csumTillCurr, total));
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "5,4,-1,7,8;23",
            "-1,2,4;6",
            "-1,-2,-4;-1",
            "-6,-2,-4;-2",
            "-6,-12,-4;-4",
            "-6,-12,-4,2,3;5"
    }
    )
    void test(String arrStr, int sum) {
        Assert.assertEquals(sum, maxSubArray(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
