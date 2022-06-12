package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * There are n buildings in a line. You are given an integer array heights of size n that represents the heights of the buildings in the line.
 *
 * The ocean is to the right of the buildings. A building has an ocean view if the building can see the ocean without obstructions. Formally, a building has an ocean view if all the buildings to its right have a smaller height.
 *
 * Return a list of indices (0-indexed) of buildings that have an ocean view, sorted in increasing order.
 *
 * Input: heights = [4,2,3,1]
 * Output: [0,2,3]
 * Explanation: Building 1 (0-indexed) does not have an ocean view because building 2 is taller.
 * Input: heights = [4,3,2,1]
 * Output: [0,1,2,3]
 * Explanation: All the buildings have an ocean view.
 * Input: heights = [1,3,2,4]
 * Output: [3]
 * Explanation: Only building 3 has an ocean view.
 *
 *
 *
 * Constraints:
 *
 *     1 <= heights.length <= 10^5
 *     1 <= heights[i] <= 10^9
 */
public class P1762BuildingWithOceanView {
    int[] findBuildings(int[] heights) {
        int[] maxRightSideHeight = new int[heights.length];
        maxRightSideHeight[heights.length-1] = -1;
        for (int i = heights.length-2; i >=0; i--) {
            maxRightSideHeight[i] = Math.max(heights[i+1], maxRightSideHeight[i+1]);
        }
        List<Integer> oceanView = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            if(heights[i] > maxRightSideHeight[i]){
                oceanView.add(i);
            }
        }
        return oceanView.stream().mapToInt(Integer::intValue).toArray();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "4,2,3,1|0,2,3",
            "4,3,2,1|0,1,2,3",
            "1,3,2,4|3",
            "1|0",
            "1,1,1|2"
    })
    void test(String heights, String expected){
        int[] expectedArr = Arrays.stream(expected.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] heightsArr = Arrays.stream(heights.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertArrayEquals(expectedArr, findBuildings(heightsArr));
    }

}
