package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Input: nums = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Input: nums = [-1,0,3,5,9,12], target = 2
 * Output: -1
 *
 *     1 <= nums.length <= 104
 *     -104 < nums[i], target < 104
 *     All the integers in nums are unique.
 *     nums is sorted in ascending order.
 */
public class P704BinarySearch {
    int binarySearch(int[] nums, int target){
        if(nums == null ||nums.length == 0){
            return -1;
        }
        if(nums.length ==1){
            return nums[0] == target?0:-1;
        }
        int beg = 0, end = nums.length-1;
        while (beg < end) {
            int mid = beg + (end - beg) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                end = mid;
            } else {
                beg = mid + 1;
            }
        }
        return nums[beg] == target ? beg:-1;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-1,0,3,5,9,12|9|4",
            "-1,0,3,5,9,12|2|-1",
            "2|2|0",
            "2,5|5|1",
    })
    void test(String numsStr, int target, int expected){
        Assertions.assertEquals(expected, binarySearch(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray(), target));
    }
}
