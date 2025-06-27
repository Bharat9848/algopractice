package array;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.StringParser;

import java.util.Arrays;

/**
 *You are given an integer array, nums, and you need to handle multiple queries of the following type:
 *
 *     Query: Calculate the sum of elements in nums between indices i and j (inclusive), where i <= j.
 *
 * Implement the NumArray class to support the following operations efficiently:
 *
 *     Constructor: Initializes the object with the integer array nums.
 *
 *     sumRange(i, j): Returns the sum of the elements of nums between indices i and j (inclusive), i.e., the sum of nums[i] + nums[i + 1] + ... + nums[j].
 *
 * Constraints:
 *
 *     1≤1≤ nums.length ≤103≤103
 *
 *     −105≤−105≤ nums[i] ≤105≤105
 *
 *     0≤0≤ i ≤≤ j << nums.length
 *
 *     At most, 103103 calls will be made to sumRange.
 */
public class QuerySumImmutable {
    private class NumArray {
        private int[] cumulative;
        public NumArray(int[] nums) {
            if(nums!=null  && nums.length > 0){
            cumulative = new int[nums.length];
            cumulative[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                cumulative[i] = cumulative[i-1] + nums[i];
            }}
        }

        public int sumRange(int i, int j) {
            int lower =0;
            if(i > 0){
                lower = cumulative[i-1];
            }
            int higher = cumulative[j];
            return higher - lower;
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "NumArray,sum,sum|[[-2,0,3,-5,2,-1],[0,2],[2,4]]|,1,0"
    })
    void test(String operstr, String argStr, String expectedStr){
        String[] expected = expectedStr.split(",");
        String[] operations = operstr.split(",");
        int[][] arguments = StringParser.parseIntArrayString(argStr, "\\[((-)?\\d+,)*(-)?\\d+\\]");
        for (int i = 0; i < arguments.length; i++) {
            System.out.println(Arrays.toString(arguments[i]));
        }
        NumArray arr = new NumArray(arguments[0]);
        for (int i = 1; i < operations.length; i++) {
            Assertions.assertEquals(Integer.parseInt(expected[i]), arr.sumRange(arguments[i][0], arguments[i][1]));
        }
    }
}
