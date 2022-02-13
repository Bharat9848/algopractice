package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
 *
 * Input: nums = [0,1]
 * Output: 2
 * Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.
 *
 * Example 2:
 *
 * Input: nums = [0,1,0]
 * Output: 2
 * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     nums[i] is either 0 or 1.
 */
public class P525ContiguousArray {
    int findMaxLength(int[] nums) {
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        sumToIndex.put(0, -1);
        int maxLen = 0, sum = 0 ;
        for (int i = 0; i < nums.length; i++) {
            sum += (nums[i] == 0 ? -1:1);
            if(sumToIndex.containsKey(sum)){
                maxLen = Math.max(maxLen, i- sumToIndex.get(sum));
            }else{
                sumToIndex.put(sum, i);
            }
        }
        return maxLen;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,1|2",
            "1,1,1,1,1|0",
            "1,1,1,1,1,0,0,0,0,0|10",
            "1,0,0,1,1,1,0,0,0|8",
            "0,1,0|2",
    })
    void test(String numStr, int expected){
        int[] nums = Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, findMaxLength(nums));
    }
}
