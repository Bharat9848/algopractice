package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array such that nums[i] == nums[j] and abs(i - j) <= k.
 *
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 *
 * Input: nums = [1,0,1,1], k = 1
 * Output: true
 *
 * Input: nums = [1,2,3,1,2,3], k = 2
 * Output: false
 *
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     -109 <= nums[i] <= 109
 *     0 <= k <= 105
 */
public class P219ContainsDuplicateII {
    boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(numToIndex.containsKey(num)){
                int lastIndex = numToIndex.get(num);
                if(i-lastIndex <= k){
                    return true;
                }else{
                    numToIndex.put(num, i);
                }
            }else {
                numToIndex.put(num, i);
            }
        }
        return false;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,1|2|false",
            "1|2|false",
            "1,2,3,1|3|true",
            "1,0,1,1|1|true",
            "1,2,3,1,2,3|2|false",
    })
    void test(String numsStr, int k, boolean expected){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, containsNearbyDuplicate(nums, k));
    }
}
