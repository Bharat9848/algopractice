package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1]
 * Output: true
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4]
 * Output: false
 *
 * Example 3:
 *
 * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     -109 <= nums[i] <= 109
 */
public class P217ContainDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Map<Integer,Boolean> present = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(present.getOrDefault(nums[i], false)){
                return true;
            }
            present.putIfAbsent(nums[i], true);
        }
        return false;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,1,1,3,3,4,3,2,4,2;true",
            "1,2,3,4;false",
            "1,2,3,1;true"
    })
    void test(String arr, boolean expected){
        Assert.assertEquals(expected, containsDuplicate(Arrays.stream(arr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
