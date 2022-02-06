package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice. The relative order of the elements should be kept the same.
 *
 * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
 *
 * Return k after placing the final result in the first k slots of nums.
 *
 * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
 *
 * Custom Judge:
 *
 * The judge will test your solution with the following code:
 *
 * int[] nums = [...]; // Input array
 * int[] expectedNums = [...]; // The expected answer with correct length
 *
 * int k = removeDuplicates(nums); // Calls your implementation
 *
 * assert k == expectedNums.length;
 * for (int i = 0; i < k; i++) {
 *     assert nums[i] == expectedNums[i];
 * }
 *
 * If all assertions pass, then your solution will be accepted.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3]
 * Output: 5, nums = [1,1,2,2,3,_]
 * Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 * Example 2:
 *
 * Input: nums = [0,0,1,1,1,1,2,3,3]
 * Output: 7, nums = [0,0,1,1,2,3,3,_,_]
 * Explanation: Your function should return k = 7, with the first seven elements of nums being 0, 0, 1, 1, 2, 3 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 *
 *
 * Constraints:
 *
 *     1 <= nums.length <= 3 * 104
 *     -104 <= nums[i] <= 104
 *     nums is sorted in non-decreasing order.
 */
public class P80RemoveDuplicate2 {
    int removeDuplicates(int[] nums) {
        int flaggedIndex = -1;
        int currentNo=nums[0];
        boolean duplicateSeen = false;
        int i = 1;
        for (; i < nums.length; i++) {
            if(flaggedIndex==-1){
                if(nums[i] == currentNo){
                    if(!duplicateSeen){
                        duplicateSeen=true;
                    }else{
                        flaggedIndex =i;
                    }
                }else{
                    currentNo = nums[i];
                    duplicateSeen=false;
                }
                continue;
            }

            if(nums[i] == currentNo){
                if(!duplicateSeen){
                    duplicateSeen=true;
                    nums[flaggedIndex]  = nums[i];
                    flaggedIndex++;
                }
            }else{
                currentNo = nums[i];
                duplicateSeen=false;
                nums[flaggedIndex] = nums[i];
                flaggedIndex++;
            }
        }
        return flaggedIndex==-1? nums.length: flaggedIndex;
    }
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0,0,1,1,1,1,2,3,3|7|0,0,1,1,2,3,3",
            "1,1,1,2,2,3|5|1,1,2,2,3",
            "1,1,1|2|1,1",
            "1,1,2,2,3,3,4,4,5,5|10|1,1,2,2,3,3,4,4,5,5",
            "1,2,3,4,5|5|1,2,3,4,5"
    })
    void test(String numsStr, int expected, String expectedNumsStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] numsExpected = Arrays.stream(expectedNumsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, removeDuplicates(nums));
        Assertions.assertArrayEquals(numsExpected, Arrays.copyOfRange(nums,0, numsExpected.length));
    }
}
