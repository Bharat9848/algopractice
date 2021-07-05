package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 * <p>
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * <p>
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 2 <= nums.length <= 105
 * -30 <= nums[i] <= 30
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * <p>
 * <p>
 * <p>
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */

class P238ProductArrExceptSelf {
    int[] productExceptSelf(int[] nums) {
        if (nums.length < 2) throw new RuntimeException("Required array length more than 2");
        int[] result = new int[nums.length];
        result[0] = calculateProductExcept(nums, 0);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                result[i] = calculateProductExcept(nums, i);
            } else {
                result[i] = (result[i - 1] / nums[i]) * nums[i - 1];
            }
        }
        return result;
    }

    private int calculateProductExcept(int[] nums, int index) {
        return IntStream.range(0, nums.length).filter(i -> i!=index).map(i -> nums[i]).reduce(1, (a,b)-> a*b);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1,2,3,4;24,12,8,6",
            "-1,1,0,-3,3;0,0,9,0,0"
    })
    void test(String inputArr, String expected) {
        int[] expectedArr = Arrays.stream(expected.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(Arrays.toString(expectedArr), Arrays.toString(productExceptSelf(Arrays.stream(inputArr.split(",")).mapToInt(Integer::parseInt).toArray())));
    }
}
