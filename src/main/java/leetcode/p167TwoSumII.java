package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
Given an array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number.

Return the indices of the two numbers (1-indexed) as an integer array answer of size 2, where 1 <= answer[0] < answer[1] <= numbers.length.

The tests are generated such that there is exactly one solution. You may not use the same element twice.

Example 1:

Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.

Example 2:

Input: numbers = [2,3,4], target = 6
Output: [1,3]

Example 3:

Input: numbers = [-1,0], target = -1
Output: [1,2]



Constraints:

    2 <= numbers.length <= 3 * 104
    -1000 <= numbers[i] <= 1000
    numbers is sorted in non-decreasing order.
    -1000 <= target <= 1000
    The tests are generated such that there is exactly one solution.


 */
class p167TwoSumII {

    int[] twoSum(int[] numbers, int target){
        if(numbers.length<2){throw new RuntimeException("invalidConstranint");}
        int i = 0,j;
        for (; i < numbers.length; i++) {
            int searchKey = target - numbers[i];
            if(searchKey >= numbers[i]){
                j = binarySearch(numbers, i+1, numbers.length-1, searchKey);
                if(j != -1){
                    return new int[]{i+1, j+1};
                }
            }else {
                System.out.printf("number %d and searchKey %d \n", numbers[i], searchKey);
                throw new RuntimeException("No specs");
            }
        }
        throw new RuntimeException("no solution found");
    }

    int binarySearch(int[] nums, int beg, int end, int key){
        if(beg==end && nums[beg] == key){return beg;}
        while(beg <= end){
            int mid = beg + (end-beg)/2;
            if(nums[mid] == key){
                return mid;
            }else if(nums[mid] > key){
                end = mid - 1;
            }else{
                beg = mid + 1;
            }
        }
        return -1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {"2,3,4;6;1,3",
    "-1,0;-1;1,2",
    "2,4,8,10,12,15;22;4,5",
    "1,2,3,3;6;3,4"})
    void test(String numStr, int target, String expectedStr){
        int[] numbers = Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(twoSum(numbers, target)));
    }
}
