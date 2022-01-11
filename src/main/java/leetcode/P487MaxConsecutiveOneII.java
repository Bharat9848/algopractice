package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most one 0.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,0,1,1,0]
 * Output: 4
 * Explanation: Flip the first zero will get the maximum number of consecutive 1s. After flipping, the maximum number of consecutive 1s is 4.
 *
 * Example 2:
 *
 * Input: nums = [1,0,1,1,0,1]
 * Output: 4
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     nums[i] is either 0 or 1.
 *
 *
 *
 * Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
 */
public class P487MaxConsecutiveOneII {
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int leftOnes = 0;
        int rightOnes = 0;
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] == 0) {
                break;
            } else {
                leftOnes += 1;
            }
        }
        if(i == nums.length){
            return leftOnes;
        }
        i = i + 1;
        for (; i < nums.length; i++) {
            if (nums[i] == 1) {
                rightOnes += 1;
            } else {
                if (nums[i] == 0) {
                    max = Math.max(max, leftOnes + rightOnes);
                    leftOnes = rightOnes;
                    rightOnes = 0;
                }
            }
        }
        return Math.max(max, leftOnes + rightOnes) + 1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,1,1,1|4",
            "1,0,1,1,0,1|4",
            "1,0,1,1,0|4",
            "1,0,1,1,1|5",
            "0,1,1,1,1,1|6",
            "1,1,1,1,1,0|6",
            "0,1,1,0|3"
    })
    void test(String arrStr, int expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findMaxConsecutiveOnes(nums));
    }
}
