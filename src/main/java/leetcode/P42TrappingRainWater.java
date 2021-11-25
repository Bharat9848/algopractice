package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 *
 *
 *
 * Example 1:
 *
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 *
 * Example 2:
 *
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * DP solution:
 * Algorithm
 *
 *     Find maximum height of bar from the left end upto an index i in the array left_max\text{left\_max}left_max.
 *     Find maximum height of bar from the right end upto an index i in the array right_max\text{right\_max}right_max.
 *     Iterate over the height\text{height}height array and update ans:
 *         Add min⁡(left_max[i],right_max[i])−height[i]\min(\text{left\_max}[i],\text{right\_max}[i]) - \text{height}[i]min(left_max[i],right_max[i])−height[i] to ans\text{ans}ans
 *
 * Constraints:
 *
 *     n == height.length
 *     1 <= n <= 2 * 10^4
 *     0 <= height[i] <= 10^5
 */
class P42TrappingRainWater {
    int trap(int[] height){
        int[] leftWall = new int[height.length];
        int[] rightWall = new int[height.length];
        leftWall[0] = -1;
        rightWall[height.length-1] = -1;
        for (int i = 1; i < height.length; i++) {
            if (height[i - 1] > height[i]) {
                leftWall[i] = i - 1;
            } else if (height[i - 1] == height[i]) {
                leftWall[i] = leftWall[i - 1];
            } else {
                int j;
                for (j = i-1; j >=0 ; j--) {
                    if(height[j] > height[i]){
                        break;
                    }
                }
                leftWall[i] = j >=0 ? j : -1;
                }
            }
        for (int i = height.length-2; i >=0; i--) {
            if (height[i + 1] > height[i]) {
                rightWall[i] = i + 1;
            } else if (height[i + 1] == height[i]) {
                rightWall[i] = rightWall[i + 1];
            } else {
                int j;
                for (j = i+1; j < height.length; j++) {
                    if(height[j] > height[i]){
                        break;
                    }
                }
                rightWall[i] = j < height.length ? j : -1;
            }

        }
        System.out.println(Arrays.toString(leftWall));
        System.out.println(Arrays.toString(rightWall));
        int volume = 0;
        Set<Pair<Pair<Integer, Integer>, Integer>> sectionConsidered = new HashSet<>();
        for (int i = 1; i < height.length-1; i++) {

            Pair<Pair<Integer, Integer>, Integer> currentPair = new Pair<>(new Pair<>(leftWall[i], rightWall[i]), height[i]);
            if(!sectionConsidered.contains(currentPair) && leftWall[i] != -1 & rightWall[i] != -1){
                int width = rightWall[i] - leftWall[i] - 1;
                int height1 = Math.min(height[leftWall[i]], height[rightWall[i]]) - height[i];
                volume += (width * height1);
                sectionConsidered.add(currentPair);
            }
        }

        return volume;
    }

    int trapTry1(int[] heights){
        int volume = 0;
        for (int i = 1; i < heights.length;) {
            //code to find the deepest base
            while (i< heights.length && heights[i-1] <= heights[i]){
                i++;
            }
            if(i < heights.length && heights[i-1]> heights[i]){
                while(i < heights.length && heights[i-1]> heights[i]){
                    i++;
                }
                i = i-1;
            }
            if(i < heights.length){
                int rightHeight = i+1;
                int currentTrap = 0;
                int lastMinHeight = heights[i];
                int leftHeight = i-1;
                while (leftHeight >= 0 && rightHeight < heights.length && (Math.min(heights[rightHeight],heights[leftHeight])-lastMinHeight) > 0){
                    currentTrap += (Math.min(heights[rightHeight], heights[leftHeight])- lastMinHeight)*(rightHeight-leftHeight-1);
                    lastMinHeight = Math.min(heights[rightHeight], heights[leftHeight]);
                    if(heights[leftHeight] < heights[rightHeight]){
                        leftHeight--;
                    }else if (heights[leftHeight] > heights[rightHeight]){
                        rightHeight++;
                    }else{
                        leftHeight--;
                        rightHeight++;
                    }
                }
                i = rightHeight;
                volume += currentTrap;
            }
        }
        return volume;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
           "2,1,0,1,2|4",
            "2,1,0,4|3",
            "1,2,3,4|0",
            "4,3,2,1|0",
            "4,4,4|0",
          "4,2,0,3,2,5|9",
          "0,1,0,2,1,0,1,3,2,1,2,1|6"
    })
    void test(String heightStr, int expected){
        int[] heights = Arrays.stream(heightStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assert.assertEquals(expected, trap(heights));
    }
}
