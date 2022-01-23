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
 * You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.
 *
 * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
 *
 * Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.
 *
 * Each range [a,b] in the list should be output as:
 *
 *     "a->b" if a != b
 *     "a" if a == b
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
 * Output: ["2","4->49","51->74","76->99"]
 * Explanation: The ranges are:
 * [2,2] --> "2"
 * [4,49] --> "4->49"
 * [51,74] --> "51->74"
 * [76,99] --> "76->99"
 *
 * Example 2:
 *
 * Input: nums = [-1], lower = -1, upper = -1
 * Output: []
 * Explanation: There are no missing ranges since there are no missing numbers.
 *
 *
 *
 * Constraints:
 *
 *     -109 <= lower <= upper <= 109
 *     0 <= nums.length <= 100
 *     lower <= nums[i] <= upper
 *     All the values of nums are unique.
 */
class P163MissingRanges {
    List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        int missingNext = lower;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == missingNext){
                missingNext = nums[i] + 1;
            }else {
                if(missingNext == nums[i]-1){
                    result.add(missingNext+"");
                }else{
                    result.add(missingNext + "->" +  (nums[i]-1));
                }
                missingNext = nums[i] + 1;
            }
        }
        if(missingNext == upper){
            result.add(missingNext+"");
        }else if( missingNext < upper){
            result.add(missingNext + "->" + upper);
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,1,3,50,75|0|99|2,4->49,51->74,76->99",
            "0,1,2,3|0|3|",
            "0,1,2|0|3|3",
            "1,2,3|0|3|0",
            "-1|-1|-1|"
    })
    void test(String numsStr, int lower, int upper, String missingRangeStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        List<String> expected =
                missingRangeStr == null ? Collections.emptyList():Arrays.stream(missingRangeStr.split(",")).collect(Collectors.toList());
        Assert.assertEquals(expected, findMissingRanges(nums, lower, upper));
    }

}
