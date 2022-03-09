package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array nums of integers and integer k, return the maximum sum such that there exists i < j with nums[i] + nums[j] = sum and sum < k. If no i, j exist satisfying this equation, return -1.
 *
 * Input: nums = [34,23,1,24,75,33,54,8], k = 60
 * Output: 58
 * Input: nums = [10,20,30], k = 15
 * Output: -1
 * Explanation: In this case it is not possible to get a pair sum less that 15.
 *
 *     1 <= nums.length <= 100
 *     1 <= nums[i] <= 1000
 *     1 <= k <= 2000
 */
public class P1099TwoSumlessThanK {
    int twoSumLessThanK(int[] nums, int k) {
        Arrays.sort(nums);
        int differnce = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length-1; i++) {
            int left = nums[i];
            if(k-left < 0){
                break;
            }
            int index = skewBinarySearch(nums, i+1, k-left);
            if(index <= i){
                break;
            }
            differnce = Math.min(differnce, k-(nums[index] + left));
        }
        return differnce == Integer.MAX_VALUE ? -1: k-differnce;
    }

    private int skewBinarySearch(int[] nums, int beg, int target) {
        int end = nums.length-1;
        while (beg<end){
            int mid = beg + (end - beg)/2;
            if(nums[mid] > target){
                end = mid;
            }else if(nums[mid] < target){
                beg = mid +1;
            }else {
                end = mid-1;
            }
        }
        if(nums[beg] == target){
            return beg-1;
        }else if (nums[beg] > target){
            return beg-1;
        }else{
            return beg;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "65,633,447,352,991,511,984,717,763,218,984,60,59,393,943,847,369,97,177,100,973,610,735,748,825,549,997,94,259,516,677,961,726,538,19,685,297,975,152,859,934,237,552,640,132,454,683,367,990,955,535,716,938,734,884,137,355,889,31,788,560,917,864,487,42,197,484,720,778,696,131,566,344,490,307,346,293,603,603,291|1700|1699",
            "10,20,30|31|30",
            "10,20,30|60|50",
            "10,20|60|30",
            "10,20|10|-1",
            "10,20|30|-1",
            "10,20|40|30",
            "10,20|25|-1",
            "34,23,1,24,75,33,54,8|60|58",
            "10,20,30|15|-1"
    })
    void test(String numStr, int k, int expected){
        Assertions.assertEquals(expected,twoSumLessThanK(Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray(), k));
    }
}
