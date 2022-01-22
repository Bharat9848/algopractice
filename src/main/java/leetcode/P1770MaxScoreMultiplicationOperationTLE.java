package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given two integer arrays nums and multipliers of size n and m respectively, where n >= m. The arrays are 1-indexed.
 *
 * You begin with a score of 0. You want to perform exactly m operations. On the ith operation (1-indexed), you will:
 *
 *     Choose one integer x from either the start or the end of the array nums.
 *     Add multipliers[i] * x to your score.
 *     Remove x from the array nums.
 *
 * Return the maximum score after performing m operations.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3], multipliers = [3,2,1]
 * Output: 14
 * Explanation: An optimal solution is as follows:
 * - Choose from the end, [1,2,3], adding 3 * 3 = 9 to the score.
 * - Choose from the end, [1,2], adding 2 * 2 = 4 to the score.
 * - Choose from the end, [1], adding 1 * 1 = 1 to the score.
 * The total score is 9 + 4 + 1 = 14.
 *
 * Example 2:
 *
 * Input: nums = [-5,-3,-3,-2,7,1], multipliers = [-10,-5,3,4,6]
 * Output: 102
 * Explanation: An optimal solution is as follows:
 * - Choose from the start, [-5,-3,-3,-2,7,1], adding -5 * -10 = 50 to the score.
 * - Choose from the start, [-3,-3,-2,7,1], adding -3 * -5 = 15 to the score.
 * - Choose from the start, [-3,-2,7,1], adding -3 * 3 = -9 to the score.
 * - Choose from the end, [-2,7,1], adding 1 * 4 = 4 to the score.
 * - Choose from the end, [-2,7], adding 7 * 6 = 42 to the score.
 * The total score is 50 + 15 - 9 + 4 + 42 = 102.
 *
 *
 *
 * Constraints:
 *
 *     n == nums.length
 *     m == multipliers.length
 *     1 <= m <= 103
 *     m <= n <= 105
 *     -1000 <= nums[i], multipliers[i] <= 1000
 */
class P1770MaxScoreMultiplicationOperationTLE {

    int maximumScore(int[] nums, int[] multipliers) {
        Map<Pair<Integer,Integer>, Integer> firstLastIndicesToSum = new HashMap<>();
        return maximumScoreRec(nums, multipliers, 0, 0, nums.length-1, firstLastIndicesToSum);
    }

    private int maximumScoreRec(int[] nums, int[] multipliers, int index, int left, int right, Map<Pair<Integer, Integer>, Integer> firstLastIndicesToSum) {
        if(index >= multipliers.length){
            return 0;
        }
        int usingLeft = multipliers[index]* nums[left];
        int usingRight = multipliers[index]* nums[right];
        if(!firstLastIndicesToSum.containsKey(new Pair<>(index, left))) {
            int result = Math.max(usingLeft + maximumScoreRec(nums, multipliers, index + 1, left + 1, right, firstLastIndicesToSum), usingRight + maximumScoreRec(nums, multipliers, index + 1, left, right - 1, firstLastIndicesToSum));
            firstLastIndicesToSum.put(new Pair<>(index, left), result);
        }
        return firstLastIndicesToSum.get(new Pair<>(index, left));
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3|3,2,1|14",
            "6,3,5|8,7|83",
            "-5,-3,-3,-2,7,1|-10,-5,3,4,6|102"
    })
    void test(String numStr, String mulStr, int expected){
        int[] nums = Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] multipliers = Arrays.stream(mulStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, maximumScore(nums, multipliers));
    }
}
