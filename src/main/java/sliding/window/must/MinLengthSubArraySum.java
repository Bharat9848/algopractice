package sliding.window.must;

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

/**
 * maintain current window sum if its smalller than target then increase the right pointer. if adding new number makes the window equal to target then we calculate min window. If adding a new number will increase the sum then we have to remove the numbers from the left which is equivalent of the new number or min sum which is max of it.
 */
public class MinLengthSubArraySum {
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length < 1) {
            return 0;

        }
        int l = 0, r = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            if(currentSum + current < target){
                currentSum += current;
            } else if (currentSum + current == target) {
                currentSum = target;
                minLength = Math.min(minLength, i - l + 1);
            } else {
                currentSum += current;
                minLength = Math.min(minLength, i-l+1);
                for (int j = l; j < i; j++) {
                    currentSum -= nums[j];
                    if(currentSum >= target){
                        minLength = Math.min(minLength, i-(j+1)+1);
                    } else {
                        l = j+1;
                        break;
                    }
                }

            }

        }
        return minLength == Integer.MAX_VALUE ? 0: minLength;
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
