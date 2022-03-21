package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given four integer arrays nums1, nums2, nums3, and nums4 all of length n, return the number of tuples (i, j, k, l) such that:
 *
 *     0 <= i, j, k, l < n
 *     nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 *
 * Input: nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
 * Output: 2
 * Explanation:
 * The two tuples are:
 * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
 *
 * Example 2:
 *
 * Input: nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
 * Output: 1
 *
 *
 *
 * Constraints:
 *
 *     n == nums1.length
 *     n == nums2.length
 *     n == nums3.length
 *     n == nums4.length
 *     1 <= n <= 200
 *     -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
 */
public class p454FourSumII {
    int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> firstSecondSum = new HashMap<>();
        int result = 0;
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                firstSecondSum.putIfAbsent(nums1[i] + nums2[j], 0);
                firstSecondSum.put(nums1[i] + nums2[j], firstSecondSum.get(nums1[i] + nums2[j]) + 1);
            }
        }
        for (int i = 0; i < nums3.length; i++) {
            for (int j = 0; j < nums4.length; j++) {
                result += firstSecondSum.getOrDefault(0 - (nums3[i] +nums4[j]), 0);
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
       "1,2|-2,-1|-1,2|0,2|2",
       "0|0|0|0|1",
    })
    void test(String num1Str, String num2Str, String num3Str, String num4Str, int expected){
        int[] nums1 = Arrays.stream(num1Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = Arrays.stream(num2Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums3 = Arrays.stream(num3Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums4 = Arrays.stream(num4Str.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, fourSumCount(nums1,nums2, nums3, nums4));
    }
}
