package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 *
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 *
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 * Constraints:
 *
 *     1 <= target <= 109
 *     1 <= nums.length <= 105
 *     1 <= nums[i] <= 105
 *
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log(n)).
 */
public class P209MinNoContigousElementWithGreaterEqualSumNotDone {
    public int minSubArrayLen(int target, int[] nums) {
        int leftIndex=0, rightIndex=0;
        TreeMap<Integer, Integer> sumToIndex = new TreeMap<>();
        int windowSum = 0;
        int result = nums.length;
        while(windowSum < target && rightIndex < nums.length){
            windowSum += nums[rightIndex];
            sumToIndex.put(windowSum, rightIndex);
            rightIndex++;
        }
        if(rightIndex < nums.length){
            result = rightIndex-leftIndex-1+1;
            while (rightIndex<nums.length) {
                int next = nums[rightIndex];
                Map.Entry<Integer, Integer> sumLessThanEqualNextIndexEntry = sumToIndex.floorEntry(next);
                Integer index = sumLessThanEqualNextIndexEntry.getValue();
                leftIndex = index+1;
                sumToIndex = new TreeMap<>();
                int sum = 0;
                for (int j = leftIndex; j < rightIndex+1; j++) {
                    sum = sum + nums[j];
                    sumToIndex.put(sum, j);
                }
                if(rightIndex-leftIndex+1 < result){
                    result = rightIndex-leftIndex+1;
                }
                rightIndex++;
            }
        }
        rightIndex--;
        Integer finalSum = sumToIndex.lastEntry().getKey();
        if(finalSum - target<0){
            return 0;
        }
        while (leftIndex < rightIndex){
            if(target <= finalSum-nums[leftIndex]){
                finalSum = finalSum -nums[leftIndex];
                leftIndex++;
            }else{
                break;
            }
        }
        if(rightIndex-leftIndex+1 < result){
            result = rightIndex-leftIndex+1;
        }

        System.out.println("left = " + leftIndex + " right = " + rightIndex);
        return result;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "7|2,3,1,2,4,3|2",
            "4|1,4,4|1",
            "11|1,2,3,4,5|3",
            "11|1,1,1,1,1,1,1,1|0",
            "5|1,1,1,1,1|5",
            "15|5,1,3,5,10,7,4,9,2,8|3"

            })
    void testHappyCase(int sum, String arrstr, int expected){
        int[] arr = Arrays.stream(arrstr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, minSubArrayLen(sum, arr));
    }
}
