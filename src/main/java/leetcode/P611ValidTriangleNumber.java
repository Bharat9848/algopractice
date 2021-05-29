package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

/*
Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.



Example 1:

Input: nums = [2,2,3,4]
Output: 3
Explanation: Valid combinations are:
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3

Example 2:

Input: nums = [4,2,3,4]
Output: 4

using DP have a structure which stores the sum of two no vs count
 */
class P611ValidTriangleNumber {

    private class PositionSumNode {
        int a; int b; int sum;

        PositionSumNode(int a, int b, int sum) {
            this.a = a;
            this.b = b;
            this.sum = sum;
        }
    }

    public int triangleNumber(int[] nums){
        int result = 0;
        if (nums == null || nums.length < 3) return 0;
        Arrays.sort(nums);
        TreeMap<Integer, Integer> comboSum = new TreeMap<>();
        comboSum.put( nums[0] + nums[1], 1);
        for (int i = 2; i < nums.length; i++) {
            int candidate = nums[i];
            for(Integer sumKey: comboSum.tailMap(candidate,false).keySet()){
                result += comboSum.get(sumKey);
            }
            for (int j = i-1; j >=0; j--) {
                int newSum = nums[i] + nums[j];
                comboSum.put(newSum, comboSum.getOrDefault(newSum, 0) + 1);
            }
        }
        return result;
    }

    private int findPairsSumGreaterThan(int candidate, List<PositionSumNode> comboSum, int candIndex) {
        int beg = 0, end = comboSum.size()-1;
        while (beg<=end){
            int mid = beg + ((end-beg)/2);
            int midNum = comboSum.get(mid).sum;
            if(midNum == candidate){
                beg = mid;
                break;
            }else if(midNum > candidate){
                end = beg-1;
            }else{
                beg = mid + 1;
            }
        }
        while(beg < comboSum.size() && comboSum.get(beg).sum <= candidate){
            beg++;
        }
        if(beg < comboSum.size()  && comboSum.get(beg).sum > candidate){
            while(beg >= 0 && comboSum.get(beg).sum > candidate){
                beg--;
            }
            beg++;
        }

        return comboSum.size() - beg ;
    }


    public int triangleNumberWithoutSpace(int[] nums) {
        int result = 0;
        if (nums == null || nums.length < 3) return 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                int k = binarySearchIndex(nums[i] + nums[j], j + 1, nums.length - 1, nums);
                int triangle;
                if (nums[k] >= nums[i] + nums[j]) {
                    while (k-1 > j && nums[k] == nums[k-1]){ k--;}
                    triangle = (k - j - 1);
                } else {
                    while (k+1 < nums.length && nums[k] == nums[k+1]){ k++;}
                    triangle = (k - j);
                }
                result += triangle;
//                System.out.printf("i = %d, j = %d, k = %d, noOfTriangle = %d \t", nums[i], nums[j], nums[k], triangle);
            }
        }
        return result;
    }

    private int binarySearchIndex(int find, int beg, int end, int[] nums) {
        while (beg < end) {
            int mid = beg + ((end - beg) / 2);
            if (nums[mid] == find) {
                return mid;
            } else if (nums[mid] < find) {
                beg = mid + 1;
            } else {
                end = end - 1;
            }
        }
        return beg;
    }



    @ParameterizedTest
    @CsvSource(value = {
            "2,2,3,4;3",
            "2,3;0",
            "2,3,6;0",
            "2,3,4;1",
            "2,3,5,5,5;7",
            "0,3,11,15,23,67,82,82,92; 17"
    }, delimiter = ';')
    void test(String arr, int expected) {
        int[] nums = Arrays.stream(arr.split(",")).mapToInt(Integer::valueOf).toArray();
        Assert.assertEquals(expected, triangleNumber(nums));
    }
}
