package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,3,2,7,8,2,3,1]
 * Output: [5,6]
 *
 * Example 2:
 *
 * Input: nums = [1,1]
 * Output: [2]
 *
 *
 *
 * Constraints:
 *
 *     n == nums.length
 *     1 <= n <= 105
 *     1 <= nums[i] <= n
 *
 *
 *
 * Follow up: Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 */
class P448FindNoDisappeared {
    List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int indexToMark = Math.abs(num);
            if(nums[indexToMark-1] > 0){
                nums[indexToMark-1] = -(nums[indexToMark-1]);
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > 0){
                result.add(i+1);
            }
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,3,2,7,8,2,3,1|5,6",
            "1,1|2",
            "1,2|"
    })
    void test(String numsStr, String expected){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        List<Integer> external = expected == null? Collections.emptyList():Arrays.stream(expected.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        Assert.assertEquals(external, findDisappearedNumbers(nums));
    }
}
