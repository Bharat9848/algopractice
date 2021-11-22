package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * There is an integer array nums sorted in ascending order (with distinct values).
 *
 * Prior to being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 *
 * Given the array nums after the rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * Input: nums = [1], target = 0
 * Output: -1

 * Constraints:
 *
 *     1 <= nums.length <= 5000
 *     -104 <= nums[i] <= 104
 *     All values of nums are unique.
 *     nums is guaranteed to be rotated at some pivot.
 *     -104 <= target <= 104
 *
 *     two slopes one start from random element to highest point. Other starts from 0th index until pivot element. Target number can be in any of the slope.
 *     number can be in big slope or number can be less slope.
 *     find the target if its greater than left pointer and greater than right pointer than target need to be Bserached in first high no slope.
 *     if target is lower than right and left then if lie in lower no slope and need to be bserach in second half.
 *
 *    find the partition
 */
class P33SearchInRotatedArray {
    int search(int[] nums, int target) {
        int partitionPoint = findPartitionPoint(nums);
        int beg=0, end=nums.length-1;
        if (partitionPoint != 0) {
            if(target >= nums[0] &&  target <= nums[partitionPoint-1]){
                end = partitionPoint - 1;
            } else {
                beg = partitionPoint;
            }
        }
        while (beg <= end){
            int mid = beg+(end-beg)/2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target ){
                beg = mid+1;
            }else{
                end = mid-1;
            }
        }
        return -1;
    }

    private int findPartitionPoint(int[] nums) {
        int beg = 0, end = nums.length - 1;
        while(beg<end){
            int mid = beg + (end - beg)/2;
            if(nums[mid] > nums[beg] && nums[mid] > nums[end]){
                beg = mid+1; // mid is on first slope
            }else if(nums[mid] < nums[beg] && nums[mid] < nums[end]){
                end = mid; // mid is on second slope
            }else {
                break;
            }
        }
        return nums[beg] < nums[end] ? beg : end;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4,5,6,7,0,1,2;4;0",
            "4,5,6,7,0,1,2;2;6",
            "1;0;-1",
            "1,3;3;1",
            "4,5,6,8,2,3;1;-1",
            "4,5,6,8,2,3;9;-1",
            "4,5,6,8,2,3;8;3"
    })
    void test(String rotatedArrStr, int target, int expected){
        Assert.assertEquals(expected, search(Arrays.stream(rotatedArrStr.split(",")).mapToInt(Integer::parseInt).toArray(), target));
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4,5,6,7,0,1,2;4",
            "4,7,0,1,2;2",
            "1;0",
            "4,5,6,8,2,3;4",
            "4,5,6,8,2,3;4",
            "4,5,6,8,9,10;0"
    })
    void testPartition(String rotatedArrStr, int expected){
        Assert.assertEquals(expected, findPartitionPoint(Arrays.stream(rotatedArrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
