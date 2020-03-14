package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

public class P300LongestIncreasingSequence {
    /*
    Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.

Note:

    There may be more than one LIS combination, it is only necessary for you to return the length.
    Your algorithm should run in O(n2) complexity.

Follow up: Could you improve it to O(n log n) time complexity?
Algo: If at an index i, we now maximum of all the sequences length for which int(i) can be part of the solution.
To memoize we can have a subSolution array which holds the maximum increasing subsequence length at each index. And then we just iterate the array from index i-1 and see if int(i) is greater than int(j) then we increase the subSolution length by one. And to reach solution we will take maximum of all such cases
     */
    int lengthOfLIS(int[] nums) {
        if(nums.length==0) return 0;
        int[] subSolution = new int[nums.length];
        subSolution[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 1;
            for (int j = i-1; j >=0 ; j--) {
                if(nums[i] > nums[j]){
                    max = Math.max(subSolution[j]+1, max);
                }
            }
            subSolution[i] = max;
        }
//        System.out.println(Arrays.toString(subSolution));
        return Arrays.stream(subSolution).max().orElse(0);
    }

    @ParameterizedTest
    @CsvSource({
            "10:9:2:5:3:7:101:18, 4",
            "1,1",
            "1:3,2",
            "1:0:3,2",
            "1:2:3:4:5:4:3:2:1, 5"
    })
    void test(String nums, String sol){
        Assert.assertEquals(Integer.parseInt(sol), lengthOfLIS(Arrays.stream(nums.split(":")).mapToInt(Integer::parseInt).toArray()));
    }

}
