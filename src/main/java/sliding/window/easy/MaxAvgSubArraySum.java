package sliding.window.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of integers nums, and an integer k, return the maximum average of a contiguous subarray of length k.
 *
 * Constraints:
 *
 *     1≤1≤k ≤≤ nums.length ≤≤ 105105
 *
 *     −104≤−104≤nums[i] ≤104≤104
 */
public class MaxAvgSubArraySum {
    public static double findMaxAverage(int[] nums, int k) {
        //todo not needed only one max sum would be enough
        int[] sumK = new int[nums.length - k +1];

        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
        }
        sumK[0] = currentSum;
        int current= 0;
        for (int i = 1; i < nums.length-k+1; i++) {
            current = sumK[i-1];
            current -= nums[i-1];
            current += nums[i+k-1];
            sumK[i] =  current;
        }

        int maxSum = sumK[0];
        for (int i = 1; i < sumK.length; i++) {
            if(maxSum < sumK[i]){
                maxSum = sumK[i];
            }
        }
        // Replace this placeholder return statement with your code
        return maxSum/(k+0.0);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,5,-3,4,-2,7|3|3.0",
            "-3,4,-2,7,1,-6,5|4|2.5",
            "2,4,6,8,10,1|3|8.0",
            "2,1,5,1,3,2|3|3.0"
    })
    void test(String numsStr, int k, double expected){
        Assertions.assertEquals(expected, findMaxAverage(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray(), k));
    }
}
