package leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
 * <p>
 * Example 1:
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 * Note:
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 * Created by bharat on 21/5/18.
 */
public class P560SubArraySumKND {

    public int subarraySum(int[] nums, int k) {
       int cSum =0;
        Map<Integer,Integer> cSumToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            cSum = nums[i];
//            if
        }
        return 0;
    }

    @Test
    public void test() {
        Assert.assertEquals(2, subarraySum(new int[]{-624,-624,-624,-624,-624,-624,-624,-624,-624,-624}, -624));
        Assert.assertEquals(2, subarraySum(new int[]{1, 2, 3}, 3));
        Assert.assertEquals(2, subarraySum(new int[]{1, 1, 1}, 2));
        Assert.assertEquals(4, subarraySum(new int[]{1, 1, 1, 2, -3, 5}, 2));
        Assert.assertEquals(1, subarraySum(new int[]{1}, 1));
        Assert.assertEquals(1, subarraySum(new int[]{100, 1, 2, 3, 4}, 6));
        Assert.assertEquals(4, subarraySum(new int[]{100, 1, 2, 3, 100, 1, 2, 3, 4}, 3));

    }
}
