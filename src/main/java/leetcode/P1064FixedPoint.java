package leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Given an array of distinct integers arr, where arr is sorted in ascending order, return the smallest index i that satisfies arr[i] == i. If there is no such index, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [-10,-5,0,3,7]
 * Output: 3
 * Explanation: For the given array, arr[0] = -10, arr[1] = -5, arr[2] = 0, arr[3] = 3, thus the output is 3.
 *
 * Example 2:
 *
 * Input: arr = [0,2,5,8,17]
 * Output: 0
 * Explanation: arr[0] = 0, thus the output is 0.
 *
 * Example 3:
 *
 * Input: arr = [-10,-5,3,4,7,9]
 * Output: -1
 * Explanation: There is no such i that arr[i] == i, thus the output is -1.
 *
 *
 *
 * Constraints:
 *
 *     1 <= arr.length < 104
 *     -109 <= arr[i] <= 109
 * if a[i] > i means all the indices greater than i will have element different than index, solution can be in lower half
 * if a[i] < i means all the indices lower than i will have element different than index, solution can be in upper half.
 *
 * Follow up: The O(n) solution is very straightforward. Can we do better?
 */
public class P1064FixedPoint {
    public int fixedPoint(int[] arr) {
        int beg = 0, end = arr.length-1;
        while (beg < end){
            int mid = beg + (end - beg)/2;
            if(arr[mid] == mid){
                end = mid;
            }else if(arr[mid] > mid){
                end = mid;
            }else {
                beg = mid + 1;
            }
        }
        return arr[beg] == beg? beg: (arr[end] == end ? end: -1);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "-10,-5,0,3,7|3",
            "0,2,5,8,17|0",
            "-10,-5,3,4,7,9|-1",
            "0,1,2|0",
            "-1,1,2,4,6|1"
    })
    void test(String arrStr, int expected){
        Assertions.assertEquals(expected, fixedPoint(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
