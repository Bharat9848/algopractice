package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/**
 * Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,2,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2.
 * The third distinct maximum is 1.
 *
 * Example 2:
 *
 * Input: nums = [1,2]
 * Output: 2
 * Explanation:
 * The first distinct maximum is 2.
 * The second distinct maximum is 1.
 * The third distinct maximum does not exist, so the maximum (2) is returned instead.
 *
 * Example 3:
 *
 * Input: nums = [2,2,3,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2 (both 2's are counted together since they have the same value).
 * The third distinct maximum is 1.
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 10^4
 *     -2^31 <= nums[i] <= 2^31 - 1
 *
 *
 * Follow up: Can you find an O(n) solution?
 */
public class P414ThirdConscutiveMaximum {

    public int thirdMax(int[] nums) {
        Integer first = null;
        Integer second = null;
        Integer third = null;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(first == null|| second == null || third == null || num > third){
                List<Integer> triplet = computeOrder(first, second, third, num);
                first = triplet.get(0);
                if(triplet.size() >= 2) {
                    second = triplet.get(1);
                }
                if(triplet.size() == 3) {
                    third = triplet.get(2);
                }
            }
        }
        return third == null ? (first==null?0:first): third;
    }

    private List<Integer> computeOrder(Integer first, Integer second, Integer third, int num) {
        if((first != null && num == first)|| (second != null && num == second)||(third != null && num == third)){
            ArrayList<Integer> no = new ArrayList<>();
            no.add(first);
            if(second != null){
                no.add(second);
            }
            if(third != null){
                no.add(third);
            }
            return no;
        }
        if(first == null){
            return Collections.singletonList(num);
        }else if(second == null){
            second = Math.min(first, num);
            first = Math.max(first, num);
            return Arrays.asList(first, second);
        }else {
            if(num > first){
                third = second;
                second = first;
                first = num;
            }else if(num > second) {
                third = second;
                second = num;
            }else {
                third = num;
            }
            return Arrays.asList(first, second, third);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2,2,3,1|1",
            "1,2|2",
            "3,2,1|1"
    })
    void test(String numsStr, int expected){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, thirdMax(nums));
    }
}
