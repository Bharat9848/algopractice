package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Input: nums = [1,3,5,6], target = 5
 * Output: 2
 * Input: nums = [1,3,5,6], target = 2
 * Output: 1
 * Input: nums = [1,3,5,6], target = 7
 * Output: 4
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 104
 *     -104 <= nums[i] <= 104
 *     nums contains distinct values sorted in ascending order.
 *     -104 <= target <= 104
 */
class P35FindPosition {
    int findPosition(int[] nums, int target){
        if(nums == null|| nums.length==0){
            return -1;
        }
        int beg = 0, end = nums.length-1;
        while (beg<end){
            int mid = beg + (end - beg)/2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                end = mid;
            }else{
                beg = mid + 1;
            }
        }
        if(target <= nums[beg]){
             return beg;
        }else {
            return beg+1;
        }
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,3,5,6|5|2",
            "1,3,5,6|2|1",
            "1,3,5,6|7|4",
            "1,3,5,6|0|0",
    })
    void test(String numsStr, int target, int expected){
        Assertions.assertEquals(expected, findPosition(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray(), target));
    }
}