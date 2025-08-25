package dynamic.programming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO
 * You are given an integer array, nums, of length n, and an integer array, queries, of length m.
 *
 * For each element in queries, determine the maximum number of elements that can be selected from nums to form a subsequence such that the sum of the selected elements is less than or equal to the query value.
 *
 * Return an array answer of length m, where answer[i] represents the size of the largest subsequence of nums whose sum is less than or equal to queries[i].
 *
 * Constraints
 *
 *     n ==== nums.length
 *
 *     m ==== queries.length
 *
 *     1≤1≤ n, m ≤103≤103
 *
 *     1≤1≤ nums[i], queries[i] ≤105
 */
public class LongestSubsequenceWithLimitedSum {
    public int[] answerQueries(int[] nums, int[] queries) {
        return null;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "5,1,4,3,2|1,5,10,15|1,2,4,5",
            "8,10,4,2|6,20,42|2,3,4",
            "1,3,4,6,15|5,10,15|2,3,1",
            "1,3,4,6,15|5,10,20|2,3,3",
            "1,3,4,6,15|5,10,15|2,3,1",
            "1,3,4,6,15|5,10,11,12,13,14,15|2,3,3,3,3,4,1",
            "1,3,4,6,15|55,17,23|5,2,4",

    })
    void test(String numsStr, String queriesStr, String expectedStr){
        int[] nums = Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] queries = Arrays.stream(queriesStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] exoected = Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] actual = answerQueries(nums, queries);
        Assertions.assertArrayEquals(exoected, actual);
    }


}
