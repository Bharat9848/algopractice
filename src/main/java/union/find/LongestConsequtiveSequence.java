package union.find;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import union.find.core.UnionFind;
import union.find.core.UnionFindNode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Given an unsorted array, nums, your task is to return the length of the longest consecutive sequence of elements. The consecutive sequence of elements is such that there are no missing elements in the sequence. The consecutive elements can be present anywhere in the input array.
 *
 *     Note: Two elements, x and y, are called consecutive if the difference between them is equal to 1.
 *
 * Constraints:
 *
 *     0≤ nums.lengths ≤103
 *     −10^6 < nums[i] ≤10^6
 */

public class LongestConsequtiveSequence {
    public static int longestConsecutiveSequence(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        UnionFind<Integer> unionFind = new UnionFind<>();
        for (int i = 0; i < nums.length; i++) {
            unionFind.add(nums[i]);
        }
        int globalMax = 1;
        int currentMax = 1;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length-1; i++) {
            if(Math.abs(nums[i]-nums[i+1]) == 1){
                unionFind.union(nums[i], nums[i+1]);
                currentMax++;
            } else {
                if(globalMax < currentMax){
                    globalMax = currentMax;
                }
                currentMax = 1;
            }
        }

        if(globalMax < currentMax){
            globalMax = currentMax;
        }

        return globalMax;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|',
    value = {
            "1,2,3|3",
            "8,12,11,10,9,80,79,76|5",
            "1|1"
    })
    void test(String arrStr, int expected){
        int[] nums = Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray();
        Assertions.assertEquals(expected, longestConsecutiveSequence(nums));
    }
}
