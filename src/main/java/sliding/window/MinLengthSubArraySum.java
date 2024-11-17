package sliding.window;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Given an array of positive integers, nums, and a positive integer, target, find the minimum length of a contiguous subarray whose sum is greater than or equal to the target. If no such subarray is found, return 0.
 * <p>
 * Constraints:
 * <p>
 * 11 ≤≤ target ≤≤ 104104
 * 11 ≤≤ nums.length ≤≤ 103103
 * 11 ≤≤ nums[i] ≤≤ 103103
 */
public class MinLengthSubArraySum {
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length < 1) {
            return 0;

        }
        int l = 0, r = 0;
        int currentSum = 0;
        for (; r < nums.length && currentSum < target; r++) {
            currentSum = currentSum + nums[r];
        }
        if(r == nums.length && currentSum < target) {
            return 0;
        }
        r = r-1;
        while (currentSum >= target) {
            currentSum = currentSum - nums[l];
            l++;
        }
        l = l - 1;
        currentSum = currentSum + nums[l];
        int minLength = r - l + 1;
        for (int i = r+1; i < nums.length; i++) {
            currentSum = currentSum + nums[i];
            while (currentSum >= target) {
                currentSum = currentSum - nums[l];
                l++;
            }
            l = l - 1;
            currentSum = currentSum + nums[l];

            if (i - l + 1 < minLength) {
                minLength = i - l + 1;
            }
        }
        return minLength;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,1,3|5|3",
            "1,2,3,4|10|4",
            "1,2,7,1,8|9|2",
            "1,2,7,1,10|9|2",
            "1,3,4,5,2|1000|0",
            "1,3,4,5,2|12|3",
            "1,1,1,1,2,2|4|2",
            "1,1,1,1,2,2|2|1",
    })
    void test(String numsStr, int target, int expected) {
        var pattern = Pattern.compile("(\\d,)+\\d");
        var matcher = pattern.matcher(numsStr);
        int[] nums = matcher.results().map(a -> matcher.group()).flatMap(a -> Arrays.stream(a.split(","))).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, minSubArrayLen(target, nums));
    }
}
