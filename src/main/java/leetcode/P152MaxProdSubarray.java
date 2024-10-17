package leetcode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.

 For example, given the array [2,3,-2,4],
 the contiguous subarray [2,3] has the largest product = 6
    on ith iteration
        what is required from i-1 iteration: contiguousMaxProd, contiguousMinProd, nonContiguousMax
        max can be the ax of (number, all other (i-1) variables are negative)
        max can be the number*contiguousProd(i-1)
 */
public class P152MaxProdSubarray {


    public int maxProduct(int[] nums) {
        int result = nums[0];
        Integer currentPositiveMax= null , currentNegativeMin = null;
        if(nums[0] > 0){
            currentPositiveMax = nums[0];
        }else if(nums[0]  < 0 ){
            currentNegativeMin = nums[0];
        }

        for (int i = 1; i < nums.length; i++) {
            if(nums[i] == 0){
              result = Math.max(result, nums[i]);
              currentPositiveMax = null;
              currentNegativeMin = null;
            }else if(nums[i] > 0){
                if(currentPositiveMax != null && currentNegativeMin != null) {
                    currentPositiveMax = currentPositiveMax * nums[i];
                    currentNegativeMin = currentNegativeMin * nums[i];

                } else if(currentNegativeMin == null && currentPositiveMax != null) {
                    currentPositiveMax = currentPositiveMax * nums[i];
                } else if (currentNegativeMin != null && currentPositiveMax == null) {
                    currentPositiveMax = nums[i];
                    currentNegativeMin = currentNegativeMin * nums[i];
                } else {
                    currentPositiveMax = nums[i];
                }
            }else {
                if(currentPositiveMax != null && currentNegativeMin != null) {
                    int tempPositiveMax = currentPositiveMax;
                    currentPositiveMax = currentNegativeMin * nums[i];
                    currentNegativeMin = tempPositiveMax * nums[i];

                } else if(currentNegativeMin == null && currentPositiveMax != null) {
                        currentNegativeMin = currentPositiveMax * nums[i];
                        currentPositiveMax = null;
                } else if (currentNegativeMin != null && currentPositiveMax == null) {
                    currentPositiveMax = currentNegativeMin * nums[i];
                    currentNegativeMin = nums[i];
                } else {
                    currentNegativeMin = nums[i];
                }

            }
             result = currentPositiveMax == null ?  result: Math.max(result, currentNegativeMin == null ? currentPositiveMax: Math.max(currentPositiveMax, currentNegativeMin));
        }
        return result;
    }

    @ParameterizedTest
    @CsvSource( delimiter = '|',
            value = {
            "60|1,0,-1,2,3,-5,-2",
            "56|7,-2,-4",
            "4|4",
                    "6720|-7,4,5,6,-8",
            "20|-7,0,4,5,0,6,0,0,-8",
            "8640|-7,4,5,6,-8,-9",
                    "8|-7,-1,-8",
                    "0|-2,0,-1",
                    "6|2,3,-2,4",
                    "0|-2,0"
    }
    )
    public void test(int expected, String numArr){
        int[] nums = Arrays.stream(numArr.split("," )).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, maxProduct(nums));
    }


}
