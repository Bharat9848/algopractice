package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.

Example:

nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.

Algo > NoOfWays to reach target is sum of all the ways to reach (target-a(i))
 */
class P377CombinationSum4 {
    int combinationSum4(int[] nums, int target) {
        if (target <= 0 || nums.length == 0) return 0;
        int noOfWays = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target == nums[i]) {
                noOfWays += 1;
            } else {
                noOfWays += combinationSum4(nums, target - nums[i]);
            }
        }
        return noOfWays;
    }
    int combinationSum4DP(int[] nums, int target) {
       int[] noOfWays = new int[target+1];
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] <= target) noOfWays[nums[i]] = 1;
        }
        for (int i = 0; i < noOfWays.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if(i-nums[j] >= 0 )
                noOfWays[i] += noOfWays[i-nums[j]];
            }
        }
       return noOfWays[target];
    }

    @ParameterizedTest
    @CsvSource({"1:2:3, 4, 7", "3:2, 1, 0", "1:2:3, 1, 1"})
    void test(String nums, String target, String sol) {
        Assert.assertEquals(Integer.parseInt(sol), combinationSum4(Arrays.stream(nums.split(":")).mapToInt(Integer::parseInt).toArray(), Integer.parseInt(target)));
        Assert.assertEquals(Integer.parseInt(sol), combinationSum4DP(Arrays.stream(nums.split(":")).mapToInt(Integer::parseInt).toArray(), Integer.parseInt(target)));
    }

}
