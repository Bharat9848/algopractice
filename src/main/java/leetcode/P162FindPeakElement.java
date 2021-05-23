package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
A peak element is an element that is strictly greater than its neighbors.

Given an integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that nums[-1] = nums[n] = -∞.

You must write an algorithm that runs in O(log n) time.
Example 1:

Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.

Example 2:

Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.

Constraints:

    1 <= nums.length <= 1000

    -231 <= nums[i] <= 231 - 1
    nums[i] != nums[i + 1] for all valid i.

TODO do this with binary search hint is "nums[i] != nums[i + 1] for all valid i.". what do you think about search key?

 */
class P162FindPeakElement {
    int findPeakElement(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            long prev = i-1 >=0 ? nums[i-1]: Long.MIN_VALUE;
            long next = i+1 <nums.length ? nums[i+1]: Long.MIN_VALUE;
            if(nums[i]>next && nums[i]>prev){
                return i;
            }
        }
        return -1;
    }
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4;3",
            "1,2,1,3,5,6,4;1",
            "1,2,3,1;2",
            "-2147483648;0"
    })
    public void testPeakElement(String arrStr, int expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findPeakElement(nums));
    }
}
