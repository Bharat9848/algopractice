package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
 *
 *     [4,5,6,7,0,1,2] if it was rotated 4 times.
 *     [0,1,2,4,5,6,7] if it was rotated 7 times.
 *
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 *
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 *
 * You must write an algorithm that runs in O(log n) time.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 *
 * Example 2:
 *
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
 *
 * Example 3:
 *
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
 *
 *
 *
 * Constraints:
 *
 *     n == nums.length
 *     1 <= n <= 5000
 *     -5000 <= nums[i] <= 5000
 *     All the integers of nums are unique.
 *     nums is sorted and rotated between 1 and n times.
 *
 *     Mid is lower than both the the extreme : then decrease the right pointer
 *     Mid is larger than both the extreme: then increase the left pointer
 */
class P153FindMinRotArr {
    int findMin(int[] arr){
        int beg=0, end = arr.length-1;
        while (beg < end){
//            System.out.printf("beg=%d, end=%d\n", beg, end);
            int mid = beg + (end-beg)/2;
            if(arr[mid]>= arr[beg] && arr[mid] >= arr[end]){
                beg = mid + 1;
            }else if(arr[mid] <= arr[beg] && arr[mid] <= arr[end]){
                end = mid;
            }else{
                end = mid;
            }
        }
        return arr[beg];
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "11,13,15,17; 11",
            "4,5,6,7,0,1,2;0",
            "4,5,6,7,8,1,2;1",
            "8,1;1",
            "1,8;1",
            "98;98"
    })
    void test(String inputArr, int expected){
        Assert.assertEquals(expected, findMin(Arrays.stream(inputArr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }

}
