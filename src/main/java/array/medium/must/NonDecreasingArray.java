package array.medium.must;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Leetcode 665
 * Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at most one element.
 *
 * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n - 2).
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,2,3]
 * Output: true
 * Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
 *
 * Example 2:
 *
 * Input: nums = [4,2,1]
 * Output: false
 * Explanation: You cannot get a non-decreasing array by modifying at most one element.
 *
 *
 *
 * Constraints:
 *
 *     n == nums.length
 *     1 <= n <= 104
 *     -105 <= nums[i] <= 105
 */

/**
 *  ## key insights
 *   - Visualization helped - challenge is whether we can ignore i or i-1 and continue checking next number to either i and i-1. Draw all possible cases diagram.
 *   - Done number of iterations to reach at final solution
 *
 *  ## space complexity
 *  ## Time complexity
 */
public class NonDecreasingArray {
   public boolean checkPossibility(int[] nums){
       if(nums.length == 1){
           return true;
       }
       int lastNo = nums[0];
       int violation = 0;
       for(int i = 1; i < nums.length && violation <= 1; i++){
           if(nums[i] < lastNo) {
               violation++;
               if( i != 1 && nums[i] < nums[i-2]){
                 //ignore  i
                 lastNo = nums[i-1];
               } else if(i !=1 && nums[i] >= nums[i-2]){
                 // ignore i - 1  or i
                 lastNo = nums[i];
               } else {
                  lastNo = nums[i];
               }
           } else {
               lastNo = nums[i];
           }
       }
       return violation <= 1;
   }

   @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5,7,1,8|true",
            "3,4,2,3|false",
            "1,3,5|true",
            "4,2,3| true",
            "4,2,1|false",
            "1,0,5|true",
            "2,8,9|true",
            "1,9,6,7,8|true",
            "1,9,6,9,8|false"
    })
    void test(String numsStr, boolean expected){
       Assertions.assertEquals(expected, checkPossibility(Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray()));
   }
}
