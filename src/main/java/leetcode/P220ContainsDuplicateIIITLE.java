package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Given an integer array nums and two integers k and t, return true if there are two distinct indices i and j in the array such that abs(nums[i] - nums[j]) <= t and abs(i - j) <= k.
 * Input: nums = [1,2,3,1], k = 3, t = 0
 * Output: true
 * Input: nums = [1,0,1,1], k = 1, t = 2
 * Output: true
 * Input: nums = [1,5,9,1,5,9], k = 2, t = 3
 * Output: false
 * Constraints:
 *
 *     1 <= nums.length <= 2 * 104
 *     -231 <= nums[i] <= 231 - 1
 *     0 <= k <= 104
 *     0 <= t <= 231 - 1
 */
class P220ContainsDuplicateIIITLE {
    boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(k==0){return false;}
        Set<Long> kSizeWindow = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (Long prev: kSizeWindow) {
                if(Math.abs(prev - nums[i]) <= t){
                    return true;
                }
            } //O(NK)

            if(kSizeWindow.size() < k){
                kSizeWindow.add((long) nums[i]); //O(NlogK)
            }else{
                kSizeWindow.remove((long)nums[i-k]);
                kSizeWindow.add((long)nums[i]);
            }
        }
        return false;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2|0|1|false",
            "1|3|0|false",
            "1,2,3,1|3|0|true",
            "1,2,3,1|3|0|true",
            "1,0,1,1|1|2|true",
            "1,5,9,1,5,9|2|3|false",
            "-2147483648,2147483647|1|1|false",
            })
    void test(String numStr, int k, int t, boolean expected){
        Assertions.assertEquals(expected, containsNearbyAlmostDuplicate(Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray(), k,t));
    }
}
