package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * Note that you must do this in-place without making a copy of the array.
 *
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Input: nums = [0]
 * Output: [0]
 * Constraints:
 *
 *     1 <= nums.length <= 104
 *     -231 <= nums[i] <= 231 - 1
 *
 * 1,2,3,3,4,0
 * Follow up: Could you minimize the total number of operations done?
 */
public class P283MoveZeros {
    public void moveZeroes(int[] nums) {

        int firstZeroIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0){
                if(firstZeroIndex==-1){
                    firstZeroIndex = i;
                }
                if(i+1==nums.length || nums[i+1] == 0){
                    continue;
                }else{
                    swap(nums, firstZeroIndex, i + 1);
                    firstZeroIndex++;
                }
            } else {
                if(firstZeroIndex!=-1){
                    swap(nums, firstZeroIndex, i);
                    firstZeroIndex++;
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @ParameterizedTest
    @CsvSource(delimiter ='|', value = {
            "1,2,3|1,2,3",
            "0,1,0,3,12|1,3,12,0,0",
            "1,3,0,0,12|1,3,12,0,0",
            "0,0,0|0,0,0"
    })
    void testHappyCase(String input, String expectedStr){
        int[] nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
        Assert.assertArrayEquals(expected, nums);
    }
}
