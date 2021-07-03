package leetcode;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/*
Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0). Find two lines, which, together with the x-axis forms a container, such that the container contains the most water.

Notice that you may not slant the container.



Example 1:

Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.

Example 2:

Input: height = [1,1]
Output: 1

Example 3:

Input: height = [4,3,2,1,4]
Output: 16

Example 4:

Input: height = [1,2,1]
Output: 2


1...i...j...n
 highWater = Min(i,j) * (j-i)
Constraints:
Will it cover all the i and j : (i j) (i+1, j)// left++ (i+1, j-1)//right--  (i j-1) is not considered

    n == height.length
    2 <= n <= 105
    0 <= height[i] <= 104


 */
public class P11ContainerHighestWater {
    public int maxArea(int[] height) {
        if(height.length < 2) throw new RuntimeException("");
        int left = 0, right = height.length-1;
        int maxArea = Math.min(height[right], height[left]) * (right - left);
        while (left < right) {
            int newArea = Math.min(height[right], height[left]) * (right - left);
            if (maxArea < newArea) {
                maxArea = newArea;
            }
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4,3,2,1,4;16",
            "1,1;1",
            "1,2,3;2",
            "1,2,1;2"
    })
    void testArea(String heightArr, int expected){
        Assert.assertEquals(expected, maxArea(Arrays.stream(heightArr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
