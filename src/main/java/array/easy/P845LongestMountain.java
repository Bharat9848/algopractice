package array.easy;

/**
 * You may recall that an array arr is a mountain array if and only if:
 *
 *     arr.length >= 3
 *     There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
 *         arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 *         arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 *
 * Given an integer array arr, return the length of the longest subarray, which is a mountain. Return 0 if there is no mountain subarray.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [2,1,4,7,3,2,5]
 * Output: 5
 * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
 *
 * Example 2:
 *
 * Input: arr = [2,2,2]
 * Output: 0
 * Explanation: There is no mountain.
 *
 *
 *
 * Constraints:
 *
 *     1 <= arr.length <= 104
 *     0 <= arr[i] <= 104
 *
 *
 *
 * Follow up:
 *
 *     Can you solve it using only one pass?
 *     Can you solve it in O(1) space?
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

/**
 * Key insight:
 * Happy case: when number start to increase, mark the index position as strtInd  and till peak then we are parsing mountain if it comes down let it all the way come down till we notice otherwise. at next start of mountain we calculate max(max, newLen). repeat the process again
 * case 2: there is a plateau while parsing mountain we have to reset the strtInd
 * case 3: numbers are in increasing order
 * case 4 numbers are in decreasing order
 * case 5 number are equal
 * time: O(n)
 * space: O(1)
 *
 */
public class P845LongestMountain {
    public int longestMountain(int[] arr) {
        int max = 0;
        if(arr.length < 3){
            return 0;
        }
        int startInd = -1;
        for (int i = 1; i < arr.length ; i++) {
            int num = arr[i];
            if(arr[i] > arr[i-1]){
               if(startInd == -1){
                   startInd = i-1;
               }
            } else if(arr[i] == arr[i-1]){
                if(startInd != -1){
                    startInd = -1;
                }
            } else {
                if(startInd  != -1){
                    while(i < arr.length && arr[i] < arr[i-1]){
                        i++;
                    }
                    i = i-1;
                    max = Math.max(max, i - startInd +1);
                    startInd = -1;
                }
            }
        }
        return max;
    }

    @ParameterizedTest
    @CsvSource(delimiter='|', value={
            "2,1,4,7,3,2,5|5",
            "2,1,1,4,7,3,2,5|5",
            "2,1,4,7,7,3,2,5|0",
            "2,5,1,1,4,7,3,2,5|5",
            "10,10,10|0",
            "10,11,12|0",
            "10,9,8|0",
            "10,12,9|3",
    })
    void test(String arrStr, int expected){
        Assertions.assertEquals(expected, longestMountain(Arrays.stream(arrStr.split(",")).mapToInt(Integer::parseInt).toArray()));
    }
}
