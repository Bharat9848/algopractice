package leetcode;

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

Example 1:

Input: [1,3,5]
Output: 1
Example 2:

Input: [2,2,2,0,1]
Output: 0
Note:

This is a follow up problem to Find Minimum in Rotated Sorted Array.
Would allow duplicates affect the run-time complexity? How and why?

Tip : think in term of possible use cases.
 */

import org.junit.Assert;
import org.junit.Test;

public class P154FindMinimumInSortedRotatedArrayII {

    public int findMin(int[] nums) {
        return findMin(nums,0,nums.length-1);
    }

    private int findMin(int[] nums, int beg, int end) {
        while(beg<end){
            int mid = beg+(end-beg)/2;
            if(nums[mid]== nums[beg] && nums[mid]==nums[end]){
                int i = beg;
                while (i+1<nums.length &&nums[i]==nums[beg]){
//                    while (nums[i]==nums[beg]){
                    i++;
                }
                beg = i;
            }
            else if((nums[mid]>nums[beg] && nums[mid]>nums[end]) ||
                    (nums[mid] == nums[beg]&& nums[beg]>nums[end])){
                beg = mid+1;
            }else{
                end=mid;
            }
        }
        return nums[end];
    }


    @Test
    public void test(){
//        Assert.assertEquals(1,findMin(new int[]{1,2}));
        Assert.assertEquals(0,findMin(new int[]{4,5,6,7,0,1,2}));
        Assert.assertEquals(1,findMin(new int[]{3,4,5,5,5,5,5,1,2}));
        Assert.assertEquals(1,findMin(new int[]{2,1,2,2,2,2,2,2}));
        Assert.assertEquals(2,findMin(new int[]{2,2,2,2,2,2,2,2}));
        Assert.assertEquals(1,findMin(new int[]{2,2,1,2}));
        Assert.assertEquals(1,findMin(new int[]{2,1,2,2,2}));
    }
}
