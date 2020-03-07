package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.

Example 2:

Input: [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
             Total amount you can rob = 2 + 9 + 1 = 12.

Algo: at position i if we know the max rob amount for last two adjacent room then I can take the decision weather to rob i or not depends upon whats the maximum between secondLastRob+currentRob or lastRob
 */
 class P198HouseRobber {
    int rob(int[] nums) {
        if(nums.length==0) return 0;
        int secondLastRob = 0, lastRob = nums[0];
        int currentRob = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentRob = nums[i];
            if(currentRob + secondLastRob > lastRob){
                currentRob = currentRob + secondLastRob;
            }else{
                currentRob = lastRob;
            }
            secondLastRob = lastRob;
            lastRob = currentRob;
        }
        return currentRob;
    }

    @ParameterizedTest
    @CsvSource({"1:2:3:1, 4", "2:7:9:3:1, 12", "78,78", "1:100:45, 100"})
    void test(String nums, String sol){
        Assert.assertEquals(rob(Arrays.stream(nums.split(":")).mapToInt(Integer::parseInt).toArray()), Integer.parseInt(sol) );
    }
}
