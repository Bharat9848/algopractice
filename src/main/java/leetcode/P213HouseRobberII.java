package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.

Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
Wrong Algo: if at i we have the last max rob and sec last max rob to make decision. That will be difficult since the rooms are circular. so best will be to have a auxillary where each index will see the max rob sum at that index. Circular tweak -> need to know whether we are choosing in the (n-1)sum first house or not. a,b,c,d,a -> instead of finding sum till last or second last we will be storing set of element in the set.

Algo : problem boils down to if we rob the first then we cannot rob the last. in HouseRobber1 solution we choose first room by default, this means we may not choose last house if we reached a solution where we might be considering first house in n-1 solution.

 */
class P213HouseRobberII {
    int robNotWorking(int[] nums) {
        if (nums.length == 0) return 0;
        List<List<Integer>> sol = new ArrayList<List<Integer>>(nums.length);
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        sol.add(new ArrayList<>() {{
            add(0);
        }});
        int secondLastSum = 0;
        List<Integer> secLastSelection = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            secLastSelection = (i == 1) ? secLastSelection : sol.get(i - 2);
            secondLastSum = (i == 1) ? 0 : sum[i - 2];
            if (secondLastSum + nums[i] >= sum[i - 1] && i < nums.length - 1) {
                List<Integer> currentSelection = new ArrayList<>(secLastSelection);
                currentSelection.add(i);
                sol.add(i, currentSelection);
                sum[i] = secondLastSum + nums[i];
            } else if (i == nums.length - 1) {
                if (secLastSelection.contains(0)) {
                    if (secondLastSum - nums[0] + nums[i] > sum[i - 1]) {
                        sum[i] = secondLastSum - nums[0] + nums[i];
                        List<Integer> currentSelection = new ArrayList<>(secLastSelection);
                        currentSelection.remove(0);
                        currentSelection.add(i);
                        sol.add(i, currentSelection);
                    } else {
                        if (secondLastSum > sum[i - 1]) {
                            List<Integer> currentSelection = new ArrayList<>(sol.get(i - 2));
                            sol.add(i, currentSelection);
                            sum[i] = sum[i - 2];
                        } else {
                            List<Integer> currentSelection = new ArrayList<>(sol.get(i - 1));
                            sol.add(i, currentSelection);
                            sum[i] = sum[i - 1];
                        }
                    }
                } else {
                    if (secondLastSum + nums[i] >= sum[i - 1]) {
                        List<Integer> currentSelection = new ArrayList<>(secLastSelection);
                        currentSelection.add(i);
                        sol.add(i, currentSelection);
                        sum[i] = secondLastSum + nums[i];
                    } else {
                        List<Integer> currentSelection = new ArrayList<>(sol.get(i - 1));
                        sol.add(i, currentSelection);
                        sum[i] = sum[i - 1];
                    }
                }
            } else {
                List<Integer> currentSelection = new ArrayList<>(sol.get(i - 1));
                sol.add(i, currentSelection);
                sum[i] = sum[i - 1];
            }
        }
        return Arrays.stream(sum).max().orElse(0);
    }

    int rob(int[] nums){
        if(nums.length == 0 ) return 0;
        if(nums.length == 1) return nums[0];
        //not selecting first house
        int excludeFirst = robInternal(nums, 1, nums.length-1);
        // not selecting last house
        int excludeLast = robInternal(nums, 0, nums.length-2);
        return Math.max(excludeFirst, excludeLast);
    }

    private int robInternal(int[] nums, int from, int to){
        int secLastSum = 0, lastSum=nums[from];
        int currentRob = nums[from];
        for(int i=from+1; i<=to; i++){
            if(secLastSum + nums[i] >= lastSum){
                currentRob = secLastSum + nums[i];
            }else{
                currentRob = lastSum;
            }
            secLastSum = lastSum;
            lastSum = currentRob;
        }
        return currentRob;
    }

    @ParameterizedTest
    @CsvSource({"2:3:2, 3", "1:2:3:1, 4", "1,1", "1:1:1:2, 3", "1:1:3:6:7:10:7:1:8:5:9:1:4:4:3, 41",
    "2:2:4:3:2:5, 10"})
    void test(String nums, String sol) {
        Assert.assertEquals(rob(Arrays.stream(nums.split(":")).mapToInt(Integer::parseInt).toArray()), Integer.parseInt(sol));
    }
}
