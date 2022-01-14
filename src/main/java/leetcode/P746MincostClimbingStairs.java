package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.
 *
 * You can either start from the step with index 0, or the step with index 1.
 *
 * Return the minimum cost to reach the top of the floor.
 *
 *
 *
 * Example 1:
 *
 * Input: cost = [10,15,20]
 * Output: 15
 * Explanation: You will start at index 1.
 * - Pay 15 and climb two steps to reach the top.
 * The total cost is 15.
 *
 * Example 2:
 *
 * Input: cost = [1,100,1,1,1,100,1,1,100,1]
 * Output: 6
 * Explanation: You will start at index 0.
 * - Pay 1 and climb two steps to reach index 2.
 * - Pay 1 and climb two steps to reach index 4.
 * - Pay 1 and climb two steps to reach index 6.
 * - Pay 1 and climb one step to reach index 7.
 * - Pay 1 and climb two steps to reach index 9.
 * - Pay 1 and climb one step to reach the top.
 * The total cost is 6.
 *
 *
 *
 * Constraints:
 *
 *     2 <= cost.length <= 1000
 *     0 <= cost[i] <= 999
 */
public class P746MincostClimbingStairs {
    int minCostClimbingStairs(int[] cost) {
        Map<Integer, Integer> stepTocost = new HashMap<>();
        return minCostClimbingStairsRec(cost, cost.length, stepTocost);
    }

    private int minCostClimbingStairsRec(int[] cost, int step, Map<Integer, Integer> stepTocost) {
        if(step == 0||step ==1){
            return cost[step];
        }

        int lastStep = stepTocost.containsKey(step-1)? stepTocost.get(step-1) : minCostClimbingStairsRec(cost, step - 1, stepTocost);
        int secondLastStep = stepTocost.containsKey(step-2)?stepTocost.get(step-2): minCostClimbingStairsRec(cost, step - 2, stepTocost);

        int totalCost = Math.min(lastStep, secondLastStep) + ((step==cost.length)?0: cost[step]);
        stepTocost.put(step, totalCost);
        return totalCost;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,100,1,1,1,100,1,1,100,1|6",
            "10,15,20|15"
    })
    void test(String stairStr, int expected){
        int[] cost = Arrays.stream(stairStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, minCostClimbingStairs(cost));
    }
}


