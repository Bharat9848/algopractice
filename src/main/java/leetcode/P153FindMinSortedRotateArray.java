package leetcode;

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

You may assume no duplicate exists in the array.

Example 1:

Input: [3,4,5,1,2]
Output: 1
Example 2:

Input: [4,5,6,7,0,1,2]
Output: 0

 */

import org.junit.Assert;
import org.junit.Test;

public class P153FindMinSortedRotateArray {

    public int findMin1(int[] nums) {
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums[i-1]>nums[i]){
                return nums[i];
            }
        }
        return result;
    }

    public int findMin(int[] nums) {
       return findMin(nums,0,nums.length-1);
    }

    private int findMin(int[] nums, int beg, int end) {
        while(beg<end){
            int mid = beg+(end-beg)/2;
            if(nums[mid]>=nums[beg] && nums[mid]>nums[end]){
                beg = mid+1;
            }else{
                end=mid;
            }
        }
        return nums[end];
    }


    @Test
    public void test(){
        Assert.assertEquals(0,findMin(new int[]{4,5,6,7,0,1,2}));
        Assert.assertEquals(1,findMin(new int[]{3,4,5,5,5,5,5,1,2}));
    }
}
