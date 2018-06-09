package leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given an unsorted integer array, find the smallest missing positive integer.

 Example 1:

 Input: [1,2,0]
 Output: 3
 Example 2:

 Input: [3,4,-1,1]
 Output: 2
 Example 3:

 Input: [7,8,9,11,12]
 Output: 1
 Note:

 Your algorithm should run in O(n) time and uses constant extra space.


 * Created by bharat on 6/6/18.
 */
public class P41MissingPositiveNumber {
    public int firstMissingPositive(int[] nums) {
        int missing = nums.length + 1;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] <= 0 || nums[i] > nums.length){
                nums[i] = -1;
            }else {
                while (nums[i] != i + 1) {
                    if (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                        swap(nums, i, nums[i] - 1);
                    }else{
                        nums[i] =-1;
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < nums.length; j++) {
            if (nums[j] == -1) {
                missing = j + 1;
                break;
            }
        }
        return missing;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Test
    public void test() {
        Assert.assertEquals(1, firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
        Assert.assertEquals(2, firstMissingPositive(new int[]{3, 4, -1, 1}));
        Assert.assertEquals(3, firstMissingPositive(new int[]{1, 2, 0}));
        Assert.assertEquals(1, firstMissingPositive(new int[]{0}));
        Assert.assertEquals(2, firstMissingPositive(new int[]{1}));
        Assert.assertEquals(1, firstMissingPositive(new int[]{}));
    }

}
