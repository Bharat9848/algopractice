package leetcode;

import org.junit.Assert;
import org.junit.Test;

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
        int contiguousMax = nums[0] , contiguousMin = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] == 0){
                if(result < contiguousMax){
                    result = contiguousMax;
                }
            }
            int currentMax = Math.max(Math.max(nums[i], contiguousMax*nums[i]), contiguousMin*nums[i]);
            contiguousMin = Math.min(Math.min(nums[i], contiguousMin*nums[i]), contiguousMax*nums[i]);
            contiguousMax = currentMax;
            result = Math.max(result, contiguousMax);
        }
        return result;
    }

    @Test
    public void test(){
        Assert.assertEquals(4, maxProduct(new int[]{4}));
        Assert.assertEquals(20, maxProduct(new int[]{-7,0,4,5,0,6,0,0,-8}));
        Assert.assertEquals(6720, maxProduct(new int[]{-7,4,5,6,-8}));
        Assert.assertEquals(8640, maxProduct(new int[]{-7,4,5,6,-8,-9}));
        Assert.assertEquals(8, maxProduct(new int[]{-7,-1,-8}));
        Assert.assertEquals(0, maxProduct(new int[]{-2,0,-1}));
        Assert.assertEquals(6, maxProduct(new int[]{2,3,-2,4}));
    }


}
