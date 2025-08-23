package heap.easy;

/**
 * todo
 * You are given a 0-indexed array of integer nums and an integer k. Your task is to maximize a score through a series of operations. Initially, your score is set to 00.
 *
 * In each operation:
 *
 *     Select an index i (where 0≤0≤ i <<nums.length).
 *
 *     Add the value of nums[i] to your score.
 *
 *     Replace nums[i] with ceil(nums[i] / 3).
 *
 * Repeat this process exactly k times and return the highest score you can achieve.
 *
 *     The ceiling function ceil(value) is the least integer greater than or equal to value.
 *
 * Constraints:
 *
 *     1≤1≤ nums.length, k ≤103≤103
 *
 *     1≤1≤ nums[i] ≤105≤105
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 *     ## key insight: take maximum of all the number and replace it with num/3 and then again take maximum. so on till k operations are done.
 *     Unoptimized: it can be done with max heap of N size everytime we take out a max we will replace it with num/3.
 *     optimized: take max heap max K high elements and also maintain the min element of the heap. Then in other loop remove element from top and add it in result and if new num/3 is less than min element then discard it and if its greater than min element then add it in heap.
 *     ## time complexity: NlogK for creating heap  + KlogK for calculating result.
 *     ## space complexity: O(K) for the heap
 *
 */
public class MaximumScore {
    public static int maxScore (int[] nums, int k) {
            return 0;
        }


    @ParameterizedTest
    @CsvSource(delimiter='|', value={
            "100,50,30,3|3|184",
            "23,11,8|2|34",
            "23,11,8|3|42",
            "23,11,8|100|154",
            "30,3,1000|3|1446",
            "30,3,1000,1,9,5,1|5|1514",
    })
    void test(String numStr, int k, int expected){
        Assertions.assertEquals(expected, maxScore(Arrays.stream(numStr.split(",")).mapToInt(Integer::parseInt).toArray(), k));
    }
}
