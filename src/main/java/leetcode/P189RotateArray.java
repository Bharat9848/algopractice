package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 *
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 105
 *     -231 <= nums[i] <= 231 - 1
 *     0 <= k <= 105
 *
 *
 *
 * Follow up:
 *
 *     Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
 *     Could you do it in-place with O(1) extra space?
 */
class P189RotateArray {
    void rotate(int[] nums, int k) {
        if(k>=1 && k%nums.length !=0 && nums.length>1){
                int temp=nums[0], tempIndex =k%nums.length;
                int rotations = 0;
                int startIndex =0;
                while(rotations != nums.length) {
                    if(tempIndex == startIndex){
                        nums[tempIndex] = temp;
                        rotations++;
                        if(rotations == nums.length){
                            break;
                        }
                        startIndex++;
                        tempIndex = (startIndex+k)%nums.length;
                        temp = nums[startIndex];
                    }
                    int temp1 = nums[tempIndex];
                    nums[tempIndex] = temp;
                    tempIndex = (tempIndex + k)%nums.length;
                    temp = temp1;
                    rotations++;
                }
            }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1,2,3,4,5,6|3|4,5,6,1,2,3",
            "1,2,3,4,5,6,7|1|7,1,2,3,4,5,6",
            "1|8|1",
            "1,2|3|2,1",
            "1,2|4|1,2",
            "1,2,3,4,5,6,7|3|5,6,7,1,2,3,4",
            "-1,-100,3,99|2|3,99,-1,-100",
    })
    void test(String numsStr, int k, String expectedStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        rotate(nums, k);
        Assertions.assertArrayEquals(expected, nums);
    }
}
