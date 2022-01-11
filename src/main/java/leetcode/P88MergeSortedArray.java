package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 *
 * The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 *
 *
 *
 * Example 1:
 *
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
 * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
 *
 * Example 2:
 *
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 * Explanation: The arrays we are merging are [1] and [].
 * The result of the merge is [1].
 *
 * Example 3:
 *
 * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 * Output: [1]
 * Explanation: The arrays we are merging are [] and [1].
 * The result of the merge is [1].
 * Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
 *
 *
 *
 * Constraints:
 *
 *     nums1.length == m + n
 *     nums2.length == n
 *     0 <= m, n <= 200
 *     1 <= m + n <= 200
 *     -109 <= nums1[i], nums2[j] <= 109
 *
 *
 *
 * Follow up: Can you come up with an algorithm that runs in O(m + n) time?
 */
class P88MergeSortedArray {
    void merge(int[] nums1, int m, int[] nums2, int n) {
        int mergeIndex = nums1.length-1, num1Index = m-1, num2Index = n-1;
        while (num1Index >= 0 && num2Index >= 0){
            if(nums1[num1Index] >= nums2[num2Index]){
                nums1[mergeIndex] = nums1[num1Index];
                num1Index--;
            }else {
                nums1[mergeIndex] = nums2[num2Index];
                num2Index--;
            }
            mergeIndex--;
        }

        if(num2Index >= 0){
            while (num2Index >= 0){
                nums1[mergeIndex] = nums2[num2Index];
                num2Index--;
                mergeIndex--;
            }
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,5,6,0,0,0|3|1,2,3|3|1,2,3,4,5,6",
          "1,2,3,0,0,0|3|2,5,6|3|1,2,2,3,5,6",
            "0|0|1|1|1",
            "1,2,3,4|4||0|1,2,3,4"
    })
    void test(String nums1Str, int m, String nums2Str, int n, String expectedStr){
        int[] nums1 = Arrays.stream(nums1Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = nums2Str==null? new int[0]:Arrays.stream(nums2Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        merge(nums1, m, nums2, n);
        Assert.assertArrayEquals(expected, nums1);
    }
}
