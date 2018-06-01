package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * In a given integer array nums, there is always exactly one largest element.

 Find whether the largest element in the array is at least twice as much as every other number in the array.

 If it is, return the index of the largest element, otherwise return -1.
 * Created by bharat on 17/4/18.
 */
public class P747LargeAtLeastTwice {

    public int dominantIndex(int[] nums) {
        int max = 0;
        boolean cond = true;
        for (int i = 1 ; i < nums.length; i++) {
            if(nums[i] > nums[max]){
                if(nums[i] >= 2*nums[max]){
                    cond = true;
                }else{
                    cond = false;
                }
                max = i;
            }else{
                cond = cond && (nums[max] >= 2*nums[i]);
            }
        }
        return cond ? max :-1;
    }


    @Test
    public void test(){
        Assert.assertEquals(1,dominantIndex(new int[]{3, 6, 1, 0}));
        Assert.assertEquals(-1,dominantIndex(new int[]{6, 6, 1, 0}));
        Assert.assertEquals(-1,dominantIndex(new int[]{1, 2, 3, 4}));
    }
}
