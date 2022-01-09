package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given a binary array nums, return the maximum number of consecutive 1's in the array.
 *
 * Input: nums = [1,1,0,1,1,1]
 * Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
 *
 * Input: nums = [1,0,1,1,0,1]
 * Output: 2

 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     nums[i] is either 0 or 1.
 */
public class P485MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        int currentMax = 0, maxSoFar=0;
        for(int i =0; i<nums.length; i++){
            if(nums[i] == 1){
                currentMax += 1;
            }else{
                if(currentMax > maxSoFar){
                    maxSoFar = currentMax;
                }
                currentMax = 0;
            }

        }
        if(currentMax > maxSoFar){
            maxSoFar = currentMax;
        }
        return maxSoFar;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,0,1,1,0,1|2",
            "1,1,0,1,1,1|3",
            "0|0"
    })
    void test(String arrStr, int expected){
        int[] arr = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, findMaxConsecutiveOnes(arr));
    }
}
