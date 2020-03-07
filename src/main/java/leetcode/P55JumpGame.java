package leetcode;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

Example 1:

Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:

Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum
             jump length is 0, which makes it impossible to reach the last index.

Algo: at position i we need to find that its reachable and if its reachable we need to find all the position that can be reached from the position.
Q1: how to find if position is reachable -> go iteratively from position zero and mark the position reachable in auxillary array.
Technique is Dynamic programming


 */
public class P55JumpGame {
    public boolean canJumpRecursion(int[] nums) {
        return canJumpRecursive(nums, nums.length-1);
    }

    public boolean canJump(int[] nums){
        if(nums.length <= 0) {return false;}
        boolean[] reachable = new boolean[nums.length];
        reachable[0] = true;
        for (int i = 0; i < nums.length && !reachable[nums.length - 1]; i++) {
            // if i is not reachable from the previous indices then ignore it
            if(!reachable[i]){
                continue;
            }
            for (int jump = 1; i + jump < nums.length && jump <= nums[i]; jump++) {
                reachable[i + jump] = true;
            }
        }
        return reachable[nums.length - 1];
    }

    private boolean canJumpRecursive(int[] nums, int index) {
        if(index == 0){
            return true;
        } else{
            boolean canReach = false;
            for (int i = index - 1; i >= 0 && !canReach; i--) {
                if(nums[i] >= index-i){
                    canReach |= canJumpRecursive(nums, i);
                }
            }
            return canReach;
        }
    }


    @Test
    public void testHappyCase(){
        assertTrue(canJumpRecursion(new int[]{2, 3, 1, 1, 4}));
        assertTrue(canJump(new int[]{2, 3, 1, 1, 4}));
        assertFalse(canJumpRecursion(new int[]{3, 2, 1, 0, 4}));
        assertFalse(canJump(new int[]{3, 2, 1, 0, 4}));
    }

    @Test
    public void testEdgeCases(){
        assertFalse(canJump(new int[0]));
        assertTrue(canJump(new int[]{5}));
    }
}
