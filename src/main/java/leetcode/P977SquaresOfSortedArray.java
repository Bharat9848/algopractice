package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer array nums sorted in non-decreasing order, return an array of the squares of each number sorted in non-decreasing order.
 *
 * Example 1:
 *
 * Input: nums = [-4,-1,0,3,10]
 * Output: [0,1,9,16,100]
 * Explanation: After squaring, the array becomes [16,1,0,9,100].
 * After sorting, it becomes [0,1,9,16,100].
 *
 * Example 2:
 *
 * Input: nums = [-7,-3,2,3,11]
 * Output: [4,9,9,49,121]
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 104
 *     -104 <= nums[i] <= 104
 *     nums is sorted in non-decreasing order.
 *
 *
 * Follow up: Squaring each element and sorting the new array is very trivial, could you find an O(n) solution using a different approach?
 */
class P977SquaresOfSortedArray {
    int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        int left, right = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0){
                right = i;
                break;
            }
        }
        left = right-1;
        int index = 0;
        while (left >= 0 && right< nums.length){
            if(Math.abs(nums[left]) <= nums[right]){
                result[index] = nums[left] * nums[left];
                left--;
            }else{
                result[index] = nums[right] * nums[right];
                right++;
            }
            index++;
        }
        if(left >= 0){
            for (int i = left; i >= 0 ; i--) {
                result[index] = nums[i] * nums[i];
                index++;
            }
        }
        if(right < nums.length){
            for (int i = right; i<nums.length; i++) {
                result[index] = nums[i] * nums[i];
                index++;
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-7,-3,2,3,11|4,9,9,49,121",
            "1,2,3,4,5|1,4,9,16,25",
            "-5,-4,-3,-2,-1|1,4,9,16,25",
            "-4,-1,0,3,10|0,1,9,16,100"
    })
    void test(String numsArr, String expectedArr){
        int[] nums = Arrays.stream(numsArr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedArr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertArrayEquals(expected, sortedSquares(nums));
    }
}
